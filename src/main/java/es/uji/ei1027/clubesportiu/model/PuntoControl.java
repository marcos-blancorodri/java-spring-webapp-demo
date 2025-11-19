package es.uji.ei1027.clubesportiu.model;

public class PuntoControl {
    int id_punto_control;
    int id_carrera;
    String nombre_puntocontrol;

    public int getIdPuntoControl() {
        return id_punto_control;
    }

    public void setIdPuntoControl(int idPuntoControl) {
        this.id_punto_control = idPuntoControl;
    }

    public int getIdCarrera() {
        return id_carrera;
    }

    public void setIdCarrera(int idCarrera) {
        this.id_carrera = idCarrera;
    }

    public String getNombrePuntoControl() {
        return nombre_puntocontrol;
    }

    public void setNombrePuntoControl(String nombre_puntocontrol) {
        this.nombre_puntocontrol = nombre_puntocontrol;
    }
}
