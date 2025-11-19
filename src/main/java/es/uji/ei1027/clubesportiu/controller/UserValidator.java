package es.uji.ei1027.clubesportiu.controller;

import es.uji.ei1027.clubesportiu.model.Usuario;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Usuario.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Usuario usuario = (Usuario) obj;

        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            errors.rejectValue("email", "obligatorio", "El correo es obligatorio");
        }
        if (usuario.getPassword() == null || usuario.getPassword().trim().isEmpty()) {
            errors.rejectValue("password", "obligatorio", "La contraseña es obligatoria");
        }
        if (usuario.getTipo() == null ||
                (!usuario.getTipo().equals("normal") && !usuario.getTipo().equals("promotor"))) {
            errors.rejectValue("tipo", "invalido", "Debes elegir un tipo válido (normal o promotor)");
        }
    }
}
