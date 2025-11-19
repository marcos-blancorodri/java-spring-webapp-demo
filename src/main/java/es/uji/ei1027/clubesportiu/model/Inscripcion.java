package es.uji.ei1027.clubesportiu.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class Inscripcion {
    String dni_participante;
    int id_carrera;
    int numero_camiseta;
    LocalTime tiempo_meta;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate fecha;
    int pago;

    public String getDniParticipante() {
        return dni_participante;
    }

    public void setDniParticipante(String dniParticipante) {
        this.dni_participante = dniParticipante;
    }

    public int getIdCarrera() {
        return id_carrera;
    }

    public void setIdCarrera(int idCarrera) {
        this.id_carrera = idCarrera;
    }

    public int getNumeroCamiseta() {
        return numero_camiseta;
    }

    public void setNumeroCamiseta(int numeroCamiseta) {
        this.numero_camiseta = numeroCamiseta;
    }

    public LocalTime getTiempoMeta() {
        return tiempo_meta;
    }

    public void setTiempoMeta(LocalTime tiempoMeta) {
        this.tiempo_meta = tiempoMeta;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getPago() {
        return pago;
    }

    public void setPago(int pago) {
        this.pago = pago;
    }
}
