package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class RegistroTiempoDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addRegistroTiempo(RegistroTiempo registroTiempo) {
        jdbcTemplate.update("INSERT INTO RegistroTiempo (tiempo, comentarios, dni_participante, id_carrera, id_punto_control) VALUES(?, ?, ?, ?, ?)",
                registroTiempo.getTiempo(), registroTiempo.getComentario(),
                registroTiempo.getDniParticipante(), registroTiempo.getIdCarrera(), registroTiempo.getPunto_control());
    }

    public void deleteRegistroTiempo(int idRegistro) {
        jdbcTemplate.update("DELETE FROM RegistroTiempo WHERE id_registro = ?", idRegistro);
    }

    public void updateRegistroTiempo(RegistroTiempo registroTiempo) {
        jdbcTemplate.update("UPDATE RegistroTiempo SET tiempo=?, comentarios=?, dni_participante=?, id_carrera=?, id_punto_control=? WHERE id_registro=?",
                registroTiempo.getTiempo(), registroTiempo.getComentario(), registroTiempo.getDniParticipante(),
                registroTiempo.getIdCarrera(), registroTiempo.getPunto_control(), registroTiempo.getIdRegistro());
    }

    public RegistroTiempo getRegistroTiempo(int idRegistro) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM RegistroTiempo WHERE id_registro = ?",
                    new RegistroTiempoRowMapper(), idRegistro);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<RegistroTiempo> getTiemposParticipante(String dni_participante) {
        try {
            return jdbcTemplate.query( "SELECT * FROM RegistroTiempo WHERE dni_participante = ?",
                    new RegistroTiempoRowMapper(), dni_participante);
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<RegistroTiempo>();
        }
    }

    public RegistroTiempo getPosicion(int idPuntoControl,String dni) {
        try {
            return jdbcTemplate.queryForObject( "SELECT * FROM RegistroTiempo Where id_punto_control = ? and dni_participante = ?",
                    new RegistroTiempoRowMapper(), idPuntoControl, dni);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<RegistroTiempo> getPosiciones2(int idPuntoControl) {
        try {
            return jdbcTemplate.query( "SELECT * FROM RegistroTiempo WHERE id_punto_control = ?",
                    new RegistroTiempoRowMapper(),
                    idPuntoControl);
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<RegistroTiempo>();
        }
    }

    public List<Map<String, Object>> getTiemposConNombreParticipante(int idPuntoControl) {
        String sql = "SELECT " +
                "   rt.id_registro, rt.tiempo, rt.comentarios, rt.id_carrera, rt.id_punto_control, " +
                "   p.nombre AS nombre_participante " + // ¡Aquí está la magia!
                "FROM " +
                "   RegistroTiempo rt " +
                "JOIN " +
                "   participante p ON rt.dni_participante = p.dni " +
                "WHERE " +
                "   rt.id_punto_control = ?";

        return jdbcTemplate.queryForList(sql, idPuntoControl);
    }




}
