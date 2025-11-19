package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.Voluntario;
import es.uji.ei1027.clubesportiu.model.VoluntarioCompeticion;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class VoluntarioRowMapper implements RowMapper<Voluntario> {
    @Override
    public Voluntario mapRow(ResultSet rs, int rowNum) throws SQLException {
        Voluntario voluntario = new Voluntario();
        voluntario.setIdVoluntario(rs.getInt("id_voluntario"));
        voluntario.setNombre(rs.getString("nombre"));
        voluntario.setFecha_nacimiento(rs.getObject("fecha_nacimiento", LocalDate.class));
        voluntario.setGenero(rs.getString("genero"));
        voluntario.setEmail(rs.getString("email"));
        return voluntario;
    }
}