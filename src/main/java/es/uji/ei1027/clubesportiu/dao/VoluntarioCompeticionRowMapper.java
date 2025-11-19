package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.Sponsor;
import es.uji.ei1027.clubesportiu.model.VoluntarioCompeticion;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class VoluntarioCompeticionRowMapper implements RowMapper<VoluntarioCompeticion> {
    @Override
    public VoluntarioCompeticion mapRow(ResultSet rs, int rowNum) throws SQLException {
        VoluntarioCompeticion voluntario = new VoluntarioCompeticion();
        voluntario.setIdVoluntario(rs.getInt("id_voluntario"));
        voluntario.setIdCompeticion(rs.getInt("id_competicion"));

        return voluntario;
    }
}