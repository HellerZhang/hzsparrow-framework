package com.hzsparrow.validation.constraintsimpl;

import com.hzsparrow.validation.constraints.ByteLength;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.nio.charset.Charset;

public class ByteLengthValidator implements ConstraintValidator<ByteLength, String> {

    int min = -1;

    int max = -1;

    @Override
    public void initialize(ByteLength constraintAnnotation) {
        min = constraintAnnotation.min();
        max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null || s.length() == 0) {
            if (min <= 0) {
                return true;
            } else {
                return false;
            }
        } else {
            int length = s.getBytes(Charset.forName("UTF-8")).length;
            return length <= this.max && length >= this.min;
        }

    }
}
