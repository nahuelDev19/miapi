package com.api.rest.miapi.validaciones;

import java.lang.annotation.*;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = ExistFinByNameImpl.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistFinByName {

    String message() default "El UserName ya exciste en nuestros registros";  // Mensaje por defecto
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
