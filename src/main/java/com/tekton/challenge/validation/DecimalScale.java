package com.tekton.challenge.validation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;
import java.math.BigDecimal;

@Documented
@Constraint(validatedBy = DecimalScale.Validator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface DecimalScale {

    String message() default "The number must not exceed {maxScale} decimal places.";

    int maxScale();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<DecimalScale, BigDecimal> {

        private int maxScale;

        @Override
        public void initialize(DecimalScale constraintAnnotation) {
            this.maxScale = constraintAnnotation.maxScale();
        }

        @Override
        public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
            return value == null || value.scale() <= maxScale;
        }

    }

}
