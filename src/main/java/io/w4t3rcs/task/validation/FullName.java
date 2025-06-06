package io.w4t3rcs.task.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FullNameValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface FullName {
    String message() default "Invalid full name";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
