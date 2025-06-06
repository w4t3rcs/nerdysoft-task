package io.w4t3rcs.task.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = BorrowedBooksValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface BorrowedBooks {
    String message() default "Invalid borrowed books list";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
