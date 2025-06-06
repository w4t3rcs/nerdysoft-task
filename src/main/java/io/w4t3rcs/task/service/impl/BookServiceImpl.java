package io.w4t3rcs.task.service.impl;

import io.w4t3rcs.task.dto.BookNamesAmountsResponse;
import io.w4t3rcs.task.dto.BookRequest;
import io.w4t3rcs.task.dto.BookResponse;
import io.w4t3rcs.task.entity.Book;
import io.w4t3rcs.task.entity.Member;
import io.w4t3rcs.task.exception.NotFoundException;
import io.w4t3rcs.task.mapper.BookMapper;
import io.w4t3rcs.task.repository.BookRepository;
import io.w4t3rcs.task.repository.MemberRepository;
import io.w4t3rcs.task.service.BookService;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    @CachePut("books")
    public BookResponse createBook(BookRequest request) {
        if (request.getAmount() == 0) throw new ValidationException("Amount must be greater than zero");
        if (bookRepository.existsBookByTitleAndAuthor(request.getTitle(), request.getAuthor())) {
            Book book = bookRepository.findBookByTitleAndAuthor(request.getTitle(), request.getAuthor())
                    .orElseThrow(NotFoundException::new);
            book.setAmount(book.getAmount() + request.getAmount());
            Book savedBook = bookRepository.save(book);
            return bookMapper.toBookResponse(savedBook);
        }

        Book mappedBook = bookMapper.toBook(request);
        Book savedBook = bookRepository.save(mappedBook);
        return bookMapper.toBookResponse(savedBook);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookResponse> getBooks(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(bookMapper::toBookResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookResponse> getBooksByMemberName(String memberName, Pageable pageable) {
        Member member = memberRepository.findByName(memberName).orElseThrow(NotFoundException::new);
        return new PageImpl<>(member.getBooks().stream().map(bookMapper::toBookResponse).toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<String> getBookNames(Pageable pageable) {
        return new PageImpl<>(bookRepository.findAll(pageable)
                .filter(book -> !book.getMembers().isEmpty())
                .map(book -> book.getTitle() + " :"  + book.getAuthor())
                .toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookNamesAmountsResponse> getBookNamesAndAmounts(Pageable pageable) {
        return new PageImpl<>(bookRepository.findAll(pageable)
                .filter(book -> !book.getMembers().isEmpty())
                .map(book -> new BookNamesAmountsResponse(book.getTitle() + " :"  + book.getAuthor(), book.getMembers().size()))
                .toList());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("books")
    public BookResponse getBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(NotFoundException::new);
        return bookMapper.toBookResponse(book);
    }

    @Override
    @Transactional
    @CachePut("books")
    public BookResponse updateBook(Long id, BookRequest request) {
        Book book = bookRepository.findById(id).orElseThrow(NotFoundException::new);
        if (request.getTitle() != null) book.setTitle(request.getTitle());
        if (request.getAuthor() != null) book.setAuthor(request.getAuthor());
        if (request.getAmount() != null) book.setAmount(request.getAmount());
        Book savedBook = bookRepository.save(book);
        return bookMapper.toBookResponse(savedBook);
    }

    @Override
    @Transactional
    @CacheEvict("books")
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(NotFoundException::new);
        if (!book.getMembers().isEmpty()) throw new ValidationException("If book is borrowed it cannot be deleted!");
        bookRepository.delete(book);
    }
}