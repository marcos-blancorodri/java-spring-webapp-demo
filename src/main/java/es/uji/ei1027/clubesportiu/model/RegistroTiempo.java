package es.uji.ei1027.clubesportiu.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

public class RegistroTiempo {
    int id_registro;
    @DateTimeFormat(pattern = "HH:mm:ss")
    LocalTime tiempo;
    String comentarios;
    String dni_participante;
    int id_carrera;
    int id_punto_control;

    public int getIdRegistro() {
        return id_registro;
    }

    public void setIdRegistro(int idRegistro) {
        this.id_registro = idRegistro;
    }

    public LocalTime getTiempo() {
        return tiempo;
    }

    public void setTiempo(LocalTime tiempo) {
        this.tiempo = tiempo;
    }

    public String getComentario() {
        return comentarios;
    }

    public void setComentario(String comentario) {
        this.comentarios = comentario;
    }

    public String getDniParticipante() {
        return dni_participante;
    }

    public void setDniParticipante(String dni_participante) {
        this.dni_participante = dni_participante;
    }

    public int getIdCarrera() {
        return id_carrera;
    }

    public void setIdCarrera(int idCarrera) {
        this.id_carrera = idCarrera;
    }

    public int getPunto_control() {
        return id_punto_control;
    }

    public void setPunto_control(int id_punto_control) {
        this.id_punto_control = id_punto_control;
    }
}
