package es.uji.ei1027.clubesportiu.controller;

import es.uji.ei1027.clubesportiu.model.Competicion;
import es.uji.ei1027.clubesportiu.model.RegistroTiempo;
import es.uji.ei1027.clubesportiu.model.Sponsor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class RegistroTiempoValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return RegistroTiempo.class.equals(cls);
        // o, si volguérem tractar també les subclasses:
        // return Nadador.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        RegistroTiempo registroTiempo = (RegistroTiempo) obj;
        if (registroTiempo.getTiempo() == null) {
            errors.rejectValue("tiempo", "obligatori",
                    "Es obligatorio registrar un tiempo.");
        }

        if (registroTiempo.getComentario() == null) {
            errors.rejectValue("comentario", "obligatori",
                    "Tiene que haber un comentario");
        }
    }
}
