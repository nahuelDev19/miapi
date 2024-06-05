package com.api.rest.miapi.validationx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.rest.miapi.services.UserService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class ExistByUserNameValidation implements ConstraintValidator<ExistByUserName,String>{

    @Autowired
    private UserService userService;


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (userService==null) {
            return true;
        }
        return !userService.existsByUsername(value);
    }

    

}
