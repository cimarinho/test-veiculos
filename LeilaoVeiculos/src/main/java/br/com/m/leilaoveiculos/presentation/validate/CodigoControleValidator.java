package br.com.m.leilaoveiculos.presentation.validate;

import br.com.m.leilaoveiculos.application.LeilaoVeiculoApplication;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CodigoControleValidator implements ConstraintValidator<CodigoControleValidation, String> {

   @Autowired
   private LeilaoVeiculoApplication leilaoVeiculoApplication;

   public void initialize(CodigoControleValidation constraint) {
   }

   public boolean isValid(String codigoControle, ConstraintValidatorContext context) {
      return !leilaoVeiculoApplication.existeCodigoControle(codigoControle);
   }
}
