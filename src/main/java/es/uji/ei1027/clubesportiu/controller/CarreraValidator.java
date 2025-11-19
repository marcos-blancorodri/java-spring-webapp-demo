package es.uji.ei1027.clubesportiu.controller;

import es.uji.ei1027.clubesportiu.model.Carrera;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import java.util.*;

public class CarreraValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Carrera.class.equals(cls);
        // o, si volguérem tractar també les subclasses:
        // return Nadador.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Carrera carrera = (Carrera)obj;
        if (carrera.getNombre().trim().equals(""))
            errors.rejectValue("nombre", "obligatori",
                    "Cal introduir un valor");

        if (carrera.getLongitud() <= 0) {
            errors.rejectValue("longitud", "obligatori",
                    "Cal introduir una longitud y que siga positiva");
        }

        if (carrera.getFecha()== null) {
            errors.rejectValue("fecha", "obligatori",
                    "Cal introduir una fecha");
        }

        if (carrera.getPrecio()<= 0){
            errors.rejectValue("precio", "obligatori",
                    "Cal introduir un preu");
    }   }
}
