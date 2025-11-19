package es.uji.ei1027.clubesportiu.controller;

import es.uji.ei1027.clubesportiu.model.Carrera;
import es.uji.ei1027.clubesportiu.model.Competicion;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import java.util.*;

public class CompeticionValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Competicion.class.equals(cls);
        // o, si volguérem tractar també les subclasses:
        // return Nadador.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Competicion competicion = (Competicion) obj;
        if (competicion.getNombre().trim().equals(""))
            errors.rejectValue("nombre", "obligatori",
                    "Cal introduir un valor");

        if (competicion.getFecha_in()== null)
            errors.rejectValue("fecha_in", "obligatori",
                    "Cal introduir una fecha de inici");

        if (competicion.getFecha_fin()== null)
            errors.rejectValue("fecha_fin", "obligatori",
                    "Cal introduir una fecha de fi");
    }
}