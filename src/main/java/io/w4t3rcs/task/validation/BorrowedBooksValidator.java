package io.w4t3rcs.task.validation;

import io.w4t3rcs.task.config.ApplicationContextContainer;
import io.w4t3rcs.task.entity.Book;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class BorrowedBooksValidator implements ConstraintValidator<BorrowedBooks, List<Book>> {
    private Integer maxBookCount;

    @Override
    public void initialize(BorrowedBooks constraintAnnotation) {
        this.maxBookCount = ApplicationContextContainer.getApplicationContext()
                .getEnvironment()
                .getProperty("api.books.max-count", Integer.class);
    }

    @Override
    public boolean isValid(List<Book> borrowedBooks, ConstraintValidatorContext constraintValidatorContext) {
        return borrowedBooks != null && borrowedBooks.size() <= maxBookCount;
    }
}
