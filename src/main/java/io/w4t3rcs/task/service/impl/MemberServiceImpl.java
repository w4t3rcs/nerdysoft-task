package io.w4t3rcs.task.service.impl;

import io.w4t3rcs.task.dto.MemberRequest;
import io.w4t3rcs.task.dto.MemberResponse;
import io.w4t3rcs.task.entity.Book;
import io.w4t3rcs.task.entity.Member;
import io.w4t3rcs.task.exception.NotFoundException;
import io.w4t3rcs.task.mapper.MemberMapper;
import io.w4t3rcs.task.repository.BookRepository;
import io.w4t3rcs.task.repository.MemberRepository;
import io.w4t3rcs.task.service.MemberService;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final BookRepository bookRepository;

    @Override
    @Transactional
    @Cacheable({"members", "books"})
    public MemberResponse createMember(MemberRequest request) {
        Member mappedMember = memberMapper.toMember(request);
        request.getBooks().forEach(bookRequest -> {
            bookRepository.findBookByTitleAndAuthor(bookRequest.getTitle(), bookRequest.getAuthor())
                    .ifPresent(book -> {
                        Integer amount = book.getAmount();
                        if (amount <= 0) throw new ValidationException();
                        book.setAmount(amount - 1);
                        bookRepository.save(book);
                    });
        });
        Member savedMember = memberRepository.save(mappedMember);
        return memberMapper.toMemberResponse(savedMember);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("members")
    public Page<MemberResponse> getMembers(Pageable pageable) {
        return memberRepository.findAll(pageable)
                .map(memberMapper::toMemberResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public MemberResponse getMember(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(NotFoundException::new);
        return memberMapper.toMemberResponse(member);
    }

    @Override
    @Transactional
    @CachePut({"members", "books"})
    public MemberResponse updateMember(Long id, MemberRequest request) {
        Member member = memberRepository.findById(id).orElseThrow(NotFoundException::new);
        if (request.getName() != null) member.setName(request.getName());
        if (request.getBooks() != null) {
            List<Book> newBooks = request.getBooks()
                    .stream()
                    .map(bookRequest -> bookRepository.findBookByTitleAndAuthor(bookRequest.getTitle(), bookRequest.getAuthor())
                            .orElseThrow(NotFoundException::new))
                    .collect(Collectors.toList());
            List<Book> oldBooks = member.getBooks();
            boolean isOldBooksNotNull = oldBooks != null;
            for (Book newBook : newBooks) {
                if ((!isOldBooksNotNull) || (!oldBooks.contains(newBook))) {
                    System.out.println("I am here :D");
                    newBook.setAmount(newBook.getAmount() - 1);
                    bookRepository.save(newBook);
                }
            }

            if (isOldBooksNotNull) {
                for (Book oldBook : oldBooks) {
                    if (!newBooks.contains(oldBook)) {
                        oldBook.setAmount(oldBook.getAmount() + 1);
                        bookRepository.save(oldBook);
                    }
                }
            }

            member.setBooks(newBooks);
        }

        Member savedMember = memberRepository.save(member);
        return memberMapper.toMemberResponse(savedMember);
    }

    @Override
    @Transactional
    @CacheEvict("members")
    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(NotFoundException::new);
        if (!member.getBooks().isEmpty()) throw new ValidationException("If member borrows a book member cannot be deleted");
        memberRepository.delete(member);
    }
}
