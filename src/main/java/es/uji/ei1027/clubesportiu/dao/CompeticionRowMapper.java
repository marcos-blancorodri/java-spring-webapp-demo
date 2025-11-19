package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.Competicion;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class CompeticionRowMapper implements RowMapper<Competicion> {
    @Override
    public Competicion mapRow(ResultSet rs, int rowNum) throws SQLException {
        Competicion competicion = new Competicion();
        competicion.setIdCompeticion(rs.getInt("id_competicion"));
        competicion.setNombre(rs.getString("nombre"));
        competicion.setFecha_in(rs.getObject("fecha_ini", LocalDate.class));
        competicion.setFecha_fin(rs.getObject("fecha_fin",LocalDate.class));
        competicion.setDescripcion(rs.getString("descripcion"));
        competicion.setNombre_promotor(rs.getString("nombre_promotor"));
        return competicion;
    }
}
