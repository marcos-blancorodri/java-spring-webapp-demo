package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.FichaClub;

import es.uji.ei1027.clubesportiu.model.Inscripcion;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public final class InscripcionRowMapper implements RowMapper<Inscripcion> {
    @Override
    public Inscripcion mapRow(ResultSet rs, int rowNum) throws SQLException {
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setDniParticipante(rs.getString("dni_participante"));
        inscripcion.setIdCarrera(rs.getInt("id_carrera"));
        inscripcion.setNumeroCamiseta(rs.getInt("numero_camiseta"));
        inscripcion.setTiempoMeta(rs.getObject("tiempo_meta", LocalTime.class));
        inscripcion.setFecha(rs.getObject("fecha", LocalDate.class));
        inscripcion.setPago(rs.getInt("pago"));
        return inscripcion;
    }
}