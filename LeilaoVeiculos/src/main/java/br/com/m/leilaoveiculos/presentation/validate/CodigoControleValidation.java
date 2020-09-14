package br.com.m.leilaoveiculos.presentation.validate;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Target({FIELD, METHOD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = CodigoControleValidator.class)
public @interface CodigoControleValidation {
    String message() default "Codigo Controle deve ser unico. ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}