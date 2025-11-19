package es.uji.ei1027.clubesportiu.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class Carrera {
    int id_carrera;
    int longitud;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate fecha;
    int id_competicion;
    String nombre;
    int precio;

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getLongitud() {
        return longitud;
    }

    public void setLongitud(int longitud) {
        this.longitud = longitud;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getIdCarrera() {
        return id_carrera;
    }

    public void setIdCarrera(int idCarrera) {
        this.id_carrera = idCarrera;
    }

    public int getIdCompeticion() {
        return id_competicion;
    }

    public void setIdCompeticion(int idCompeticion) {
        this.id_competicion = idCompeticion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Carrera{" +
                "idCarrera=" + id_carrera +
                ", longitud=" + longitud +
                ", fecha=" + fecha +
                ", idCompeticion=" + id_competicion +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                '}';
    }
}
