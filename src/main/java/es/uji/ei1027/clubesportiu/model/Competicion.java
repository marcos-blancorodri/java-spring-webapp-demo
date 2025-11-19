package es.uji.ei1027.clubesportiu.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class Competicion {

    int id_competicion;
    String nombre;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate fecha_in;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate fecha_fin;
    String descripcion;
    String nombre_promotor;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFecha_in() {
        return fecha_in;
    }

    public void setFecha_in(LocalDate fecha_in) {
        this.fecha_in = fecha_in;
    }

    public LocalDate getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(LocalDate fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public int getIdCompeticion() {
        return id_competicion;
    }

    public void setIdCompeticion(int idCompeticion) {
        this.id_competicion = idCompeticion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre_promotor() { return nombre_promotor; }

    public void setNombre_promotor(String nombre_promotor) { this.nombre_promotor = nombre_promotor; }
}
