package com.api.rest.miapi.validaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.rest.miapi.services.UserService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class ExistFinByNameImpl implements ConstraintValidator<ExistFinByName,String>{

    @Autowired
    private UserService uService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        
        //usamos esta verificacion para que no se lance un error si service da null
        if (uService==null) {
            return true;
        }
        return !uService.existsByUsername(value);
        }

}
