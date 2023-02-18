package com.chinhnd.recruit.validation;

import com.chinhnd.recruit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailDuplicateValidationIpm implements ConstraintValidator<EmailDuplicateValidation, String> {


    @Autowired
    private UserService userService;
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !userService.isExistedUserByEmail(value);
    }
}
