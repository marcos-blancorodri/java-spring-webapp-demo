package es.uji.ei1027.clubesportiu.model;

public class SponsorCompeticion {

    int id_competicion;
    String cif_sponsor;

    public int getIdCompeticion() {
        return id_competicion;
    }

    public void setIdCompeticion(int idCompeticion) {
        this.id_competicion = idCompeticion;
    }

    public String getCifSponsor() {
        return cif_sponsor;
    }

    public void setCifSponsor(String cifSponsor) {
        this.cif_sponsor = cifSponsor;
    }
}
