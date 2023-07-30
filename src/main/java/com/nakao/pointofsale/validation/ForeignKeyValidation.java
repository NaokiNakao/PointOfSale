package com.nakao.pointofsale.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ForeignKeyValidator.class)
public @interface ForeignKeyValidation {

    String message() default "Non existing foreign key";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String tableName();
    String fieldName() default "id";

}
