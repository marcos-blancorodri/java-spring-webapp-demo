package es.uji.ei1027.clubesportiu.controller;

import es.uji.ei1027.clubesportiu.model.Sponsor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class SponsorValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Sponsor.class.equals(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Sponsor sponsor = (Sponsor) obj;

        if (sponsor.getNombre() == null || sponsor.getNombre().trim().isEmpty()) {
            errors.rejectValue("nombre", "obligatori", "Hay que introducir un nombre.");
        }

        if (sponsor.getCif() == null || sponsor.getCif().trim().isEmpty()) {
            errors.rejectValue("cif", "obligatori", "Hay que introducir un CIF.");
        }
    }
}