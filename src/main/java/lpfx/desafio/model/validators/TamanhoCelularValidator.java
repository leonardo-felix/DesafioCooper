package lpfx.desafio.model.validators;


import lpfx.desafio.model.TorcedorTelefone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TamanhoCelularValidator implements ConstraintValidator<TamanhoCelular, TorcedorTelefone> {
    public boolean isValid(TorcedorTelefone tel, ConstraintValidatorContext context) {
        return tel.getTipoTelefone().isCelular() && tel.getNumero().length() == 11;
    }
}