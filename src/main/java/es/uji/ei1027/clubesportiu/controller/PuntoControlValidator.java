package es.uji.ei1027.clubesportiu.controller;

import es.uji.ei1027.clubesportiu.model.PuntoControl;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PuntoControlValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return PuntoControl.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        PuntoControl puntoControl = (PuntoControl) obj;

        if (puntoControl.getNombrePuntoControl() == null || puntoControl.getNombrePuntoControl().trim().isEmpty()) {
            errors.rejectValue("nombrePuntoControl", "obligatori", "El nombre del punto de control es obligatorio.");
        }
    }
}