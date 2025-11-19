package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.PuntoControl;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class PuntoControlRowMapper implements RowMapper<PuntoControl> {
    @Override
    public PuntoControl mapRow(ResultSet rs, int rowNum) throws SQLException {
        PuntoControl puntoControl = new PuntoControl();
        puntoControl.setIdPuntoControl(rs.getInt("id_punto_control"));
        puntoControl.setIdCarrera(rs.getInt("id_carrera"));
        puntoControl.setNombrePuntoControl(rs.getString("nombre_puntocontrol"));
        return puntoControl;
    }
}