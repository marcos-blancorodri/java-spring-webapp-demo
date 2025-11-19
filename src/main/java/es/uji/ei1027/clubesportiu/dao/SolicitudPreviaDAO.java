package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class SolicitudPreviaDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addSolicitudPrevia(SolicitudPrevia solicitudPrevia) {
        jdbcTemplate.update("INSERT INTO SolicitudPrevia VALUES(?, ?, ?, ?, ?, ?)",
                solicitudPrevia.getNombre(), solicitudPrevia.getEstado_solicitud(), solicitudPrevia.getFechaAprobacion(),
                solicitudPrevia.getMotivo(), solicitudPrevia.getPersonaContacto(), solicitudPrevia.getMail_empresa_contacto());
    }

    public void deleteSolicitudPrevia(String nombre) {
        jdbcTemplate.update("DELETE FROM SolicitudPrevia WHERE nombre = ?", nombre);
    }

    public void updateSolicitudPrevia(SolicitudPrevia solicitudPrevia) {
        jdbcTemplate.update("UPDATE SolicitudPrevia SET estado_solicitud=?, fecha_aprobacion=?, motivo=?, persona_contacto=?, mail_empresa_contacto=? WHERE nombre=?",
                solicitudPrevia.getEstado_solicitud(), solicitudPrevia.getFechaAprobacion(), solicitudPrevia.getMotivo(),
                solicitudPrevia.getPersonaContacto(), solicitudPrevia.getMail_empresa_contacto(), solicitudPrevia.getNombre());
    }

    public SolicitudPrevia getSolicitudPrevia(String nombre) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM SolicitudPrevia WHERE nombre = ?",
                    new SolicitudPreviaRowMapper(), nombre);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}