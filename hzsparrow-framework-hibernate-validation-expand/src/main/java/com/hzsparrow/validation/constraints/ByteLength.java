package com.hzsparrow.validation.constraints;

import com.hzsparrow.validation.constraintsimpl.ByteLengthValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = ByteLengthValidator.class)
@Documented
@Repeatable(ByteLength.List.class)
public @interface ByteLength {

    int min() default 0;

    int max() default 2147483647;

    String message() default "字段长度不符合规定！";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        ByteLength[] value();
    }
}
