package es.uji.ei1027.clubesportiu.model;
import java.time.LocalDate;

public class FichaClub {

    String nombre;
    String num_federado;
    String codigo_club;
    int anyo;
    String dni_participante;
    LocalDate validez_fecha;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNum_federado() {
        return num_federado;
    }

    public void setNum_federado(String num_federado) {
        this.num_federado = num_federado;
    }

    public String getCodigo_club() {
        return codigo_club;
    }

    public void setCodigo_club(String codigo_club) {
        this.codigo_club = codigo_club;
    }

    public int getAnyo() {
        return anyo;
    }

    public void setAnyo(int anyo) {
        this.anyo = anyo;
    }

    public String getDni_participante() {
        return dni_participante;
    }

    public void setDni_participante(String dni_participante) {
        this.dni_participante = dni_participante;
    }

    public LocalDate getValidez_fecha() { return validez_fecha; }

    public void setValidez_fecha(LocalDate validez_fecha) { this.validez_fecha = validez_fecha; }
}
