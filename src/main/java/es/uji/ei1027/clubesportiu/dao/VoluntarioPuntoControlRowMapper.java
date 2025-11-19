package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.VoluntarioCompeticion;
import es.uji.ei1027.clubesportiu.model.VoluntarioPuntoControl;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class VoluntarioPuntoControlRowMapper implements RowMapper<VoluntarioPuntoControl> {
    @Override
    public VoluntarioPuntoControl mapRow(ResultSet rs, int rowNum) throws SQLException {
        VoluntarioPuntoControl voluntario = new VoluntarioPuntoControl();
        voluntario.setIdVoluntario(rs.getInt("id_voluntario"));
        voluntario.setIdPuntoControl(rs.getInt("id_punto_control"));
        return voluntario;
    }
}
