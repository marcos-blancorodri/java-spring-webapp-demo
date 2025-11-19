package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.SolicitudPrevia;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class SolicitudPreviaRowMapper implements RowMapper<SolicitudPrevia> {
    @Override
    public SolicitudPrevia mapRow(ResultSet rs, int rowNum) throws SQLException {
        SolicitudPrevia solicitudPrevia = new SolicitudPrevia();
        solicitudPrevia.setNombre(rs.getString("nombre"));
        solicitudPrevia.setEstado_solicitud(rs.getString("estado_solicitud"));
        solicitudPrevia.setFechaAprobacion(rs.getObject("fecha_aprobacion",LocalDate.class));
        solicitudPrevia.setMotivo(rs.getString("motivo"));
        solicitudPrevia.setPersonaContacto(rs.getString("persona_contacto"));
        solicitudPrevia.setMail_empresa_contacto(rs.getString("mail_empresa_contacto"));
        return solicitudPrevia;
    }
}
