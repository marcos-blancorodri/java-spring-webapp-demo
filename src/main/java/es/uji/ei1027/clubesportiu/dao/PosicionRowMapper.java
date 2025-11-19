package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.Participante;
import es.uji.ei1027.clubesportiu.model.Posicion;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class PosicionRowMapper implements RowMapper<Posicion> {
    public Posicion mapRow(ResultSet rs, int rowNum) throws SQLException {
        Posicion participante = new Posicion();
        participante.setDni(rs.getString("dni"));
        participante.setPosicion(rs.getInt("posicion"));
        participante.setId_punto_control(rs.getInt("id_punto_control"));
        return participante;
    }
}
