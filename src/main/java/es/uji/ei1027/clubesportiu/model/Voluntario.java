package es.uji.ei1027.clubesportiu.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Voluntario {

    int id_voluntario;
    String nombre;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate fecha_nacimiento;
    String genero;
    String email;

    public int getIdVoluntario() {
        return id_voluntario;
    }

    public void setIdVoluntario(int idVoluntario) {
        this.id_voluntario = idVoluntario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(LocalDate fecha_nacimiento) { this.fecha_nacimiento = fecha_nacimiento;}

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
