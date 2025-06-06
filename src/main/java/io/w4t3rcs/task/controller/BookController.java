package io.w4t3rcs.task.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.w4t3rcs.task.dto.BookNamesAmountsResponse;
import io.w4t3rcs.task.dto.BookRequest;
import io.w4t3rcs.task.dto.BookResponse;
import io.w4t3rcs.task.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Books", description = "Endpoint for book management")
@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @Operation(summary = "Creating a book and saving it to the database")
    @PostMapping
    public ResponseEntity<BookResponse> postBook(@Valid @RequestBody BookRequest request) {
        return ResponseEntity.ok(bookService.createBook(request));
    }

    @Operation(summary = "Retrieving books from the database")
    @GetMapping
    public PagedModel<BookResponse> getBooks(@ParameterObject @PageableDefault Pageable pageable) {
        return new PagedModel<>(bookService.getBooks(pageable));
    }

    @Operation(summary = "Retrieving borrowed books from the database by the member name")
    @GetMapping(params = "memberName")
    public PagedModel<BookResponse> getBooksByMemberName(@RequestParam(required = false) String memberName, @ParameterObject @PageableDefault Pageable pageable) {
        return new PagedModel<>(bookService.getBooksByMemberName(memberName, pageable));
    }

    @Operation(summary = "Retrieving borrowed books names from the database")
    @GetMapping("/borrowed-names")
    public PagedModel<String> getBookNames(@ParameterObject @PageableDefault Pageable pageable) {
        return new PagedModel<>(bookService.getBookNames(pageable));
    }

    @Operation(summary = "Retrieving borrowed books names and their amounts from the database")
    @GetMapping("/borrowed-names-amounts")
    public PagedModel<BookNamesAmountsResponse> getBookNamesAndAmounts(@ParameterObject @PageableDefault Pageable pageable) {
        return new PagedModel<>(bookService.getBookNamesAndAmounts(pageable));
    }

    @Operation(summary = "Retrieving the book from the database")
    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBook(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBook(id));
    }

    @Operation(summary = "Updating the book and saving it to the database")
    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> putBook(@PathVariable Long id, @Valid @RequestBody BookRequest request) {
        return ResponseEntity.ok(bookService.updateBook(id, request));
    }

    @Operation(summary = "Deleting the book from the database")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}
