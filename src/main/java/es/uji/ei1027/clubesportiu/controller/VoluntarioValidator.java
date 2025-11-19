package es.uji.ei1027.clubesportiu.controller;

import es.uji.ei1027.clubesportiu.model.Voluntario; // Asegúrate de que el import sea correcto
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import java.time.LocalDate;
import java.time.Period;

public class VoluntarioValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Voluntario.class.equals(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Voluntario voluntario = (Voluntario) obj;

        if (voluntario.getNombre() == null || voluntario.getNombre().trim().isEmpty()) {
            errors.rejectValue("nombre", "obligatori", "El nombre es obligatorio y no puede estar vacío.");
        }

        if (voluntario.getEmail() == null || voluntario.getEmail().trim().isEmpty()) {
            errors.rejectValue("email", "obligatori", "El email es obligatorio.");
        }

        if (voluntario.getNombre() == null || voluntario.getNombre().trim().isEmpty()) {
            errors.rejectValue("nombre", "obligatori", "El nombre es obligatorio.");
        }

        if (voluntario.getEmail() == null || !voluntario.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            errors.rejectValue("email", "format_invalid", "El formato del email no es válido.");
        }


        if (voluntario.getFecha_nacimiento() == null) {
            errors.rejectValue("fecha_nacimiento", "obligatori", "La fecha de nacimiento es obligatoria.");
        } else {
            LocalDate fechaDeHoy = LocalDate.now();


            if (voluntario.getFecha_nacimiento().isAfter(fechaDeHoy)) {
                errors.rejectValue("fecha_nacimiento", "fecha_futura", "La fecha de nacimiento no puede ser una fecha futura.");
            } else {
                Period periodo = Period.between(voluntario.getFecha_nacimiento(), fechaDeHoy);
                int anios = periodo.getYears();

                if (anios < 18) {
                    errors.rejectValue("fecha_nacimiento", "menor_edat", "El voluntario debe ser mayor de 18 años.");
                }
            }
        }
    }
}