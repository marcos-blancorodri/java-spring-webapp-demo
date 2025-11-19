package es.uji.ei1027.clubesportiu.model;

import javax.annotation.processing.Generated;
import java.time.LocalDate;

public class Participante {
    String dni;
    String nombre;
    LocalDate fecha_nacimiento;
    String genero;
    String email;
    String num_federado;

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
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

    public void setFecha_nacimiento(LocalDate fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

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

    public String getNum_federado() {
        return num_federado;
    }

    public void setNum_federado(String num_federado) {
        this.num_federado = num_federado;
    }
}
