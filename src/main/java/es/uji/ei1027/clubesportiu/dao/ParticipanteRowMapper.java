package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.Inscripcion;
import es.uji.ei1027.clubesportiu.model.Participante;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class ParticipanteRowMapper implements RowMapper<Participante> {
    public Participante mapRow(ResultSet rs, int rowNum) throws SQLException {
        Participante participante = new Participante();
        participante.setDni(rs.getString("dni"));
        participante.setNombre(rs.getString("nombre"));
        participante.setFecha_nacimiento(rs.getObject("fecha_nacimiento", LocalDate.class));
        participante.setGenero(rs.getString("genero"));
        participante.setEmail(rs.getString("email"));
        participante.setNum_federado(rs.getString("num_federado"));
        return participante;
    }
}
