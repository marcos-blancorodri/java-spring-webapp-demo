package es.uji.ei1027.clubesportiu.model;

import java.time.LocalDate;

public class SolicitudPrevia {
    String nombre;
    String estado_solicitud;
    LocalDate fecha_aprobacion;
    String motivo;
    String persona_contacto;
    String mail_empresa_contacto;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado_solicitud() {
        return estado_solicitud;
    }

    public void setEstado_solicitud(String estado_solicitud) {
        this.estado_solicitud = estado_solicitud;
    }

    public LocalDate getFechaAprobacion() {
        return fecha_aprobacion;
    }

    public void setFechaAprobacion(LocalDate fechaAprobacion) {
        this.fecha_aprobacion = fechaAprobacion;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getPersonaContacto() {
        return persona_contacto;
    }

    public void setPersonaContacto(String personaContacto) {
        this.persona_contacto = personaContacto;
    }

    public String getMail_empresa_contacto() {
        return mail_empresa_contacto;
    }

    public void setMail_empresa_contacto(String mail_empresa_contacto) {
        this.mail_empresa_contacto = mail_empresa_contacto;
    }
}
