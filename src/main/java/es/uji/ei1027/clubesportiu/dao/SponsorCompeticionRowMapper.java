package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.SponsorCompeticion;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class SponsorCompeticionRowMapper implements RowMapper<SponsorCompeticion> {
    @Override
    public SponsorCompeticion mapRow(ResultSet rs, int rowNum) throws SQLException {
        SponsorCompeticion sponsorCompeticion = new SponsorCompeticion();
        sponsorCompeticion.setCifSponsor(rs.getString("cif_sponsor"));
        sponsorCompeticion.setIdCompeticion(rs.getInt("id_competicion"));
        return sponsorCompeticion;
    }
}
