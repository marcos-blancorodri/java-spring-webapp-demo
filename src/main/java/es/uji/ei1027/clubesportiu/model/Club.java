package es.uji.ei1027.clubesportiu.model;

public class Club {
    String codigo;
    String nombre;
    String poblacion;
    String pais;
    String persona_contacto;
    String correo_contacto;
    String nif;
    String nombre_promotor;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getPersona_contacto() {
        return persona_contacto;
    }

    public void setPersona_contacto(String persona_contacto) {
        this.persona_contacto = persona_contacto;
    }

    public String getCorreo_contacto() {
        return correo_contacto;
    }

    public void setCorreo_contacto(String correo_contacto) {
        this.correo_contacto = correo_contacto;
    }

    public String getNif() { return nif; }

    public void setNif(String nif) { this.nif = nif; }

    public String getNombre_promotor() { return nombre_promotor; }

    public void setNombre_promotor(String nombre_promotor) { this.nombre_promotor = nombre_promotor; }

}
