package io.w4t3rcs.task.service;

import io.w4t3rcs.task.dto.BookNamesAmountsResponse;
import io.w4t3rcs.task.dto.BookRequest;
import io.w4t3rcs.task.dto.BookResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookResponse createBook(BookRequest request);

    Page<BookResponse> getBooks(Pageable pageable);

    Page<BookResponse> getBooksByMemberName(String memberName, Pageable pageable);

    Page<String> getBookNames(Pageable pageable);

    Page<BookNamesAmountsResponse> getBookNamesAndAmounts(Pageable pageable);

    BookResponse getBook(Long id);

    BookResponse updateBook(Long id,  BookRequest request);

    void deleteBook(Long id);
}
