package io.w4t3rcs.task.repository;

import io.w4t3rcs.task.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findBookByTitleAndAuthor(String title, String author);

    boolean existsBookByTitleAndAuthor(String title, String author);
}
