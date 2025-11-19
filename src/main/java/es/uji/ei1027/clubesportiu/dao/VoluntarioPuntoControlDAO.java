package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VoluntarioPuntoControlDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addVoluntarioPuntoControl(VoluntarioPuntoControl voluntarioPuntoControl) {
        jdbcTemplate.update("INSERT INTO Voluntario_PuntoControl VALUES(?, ?)",
                voluntarioPuntoControl.getIdVoluntario(), voluntarioPuntoControl.getIdPuntoControl());
    }

    public void deleteVoluntarioPuntoControl(int idVoluntario, int idPuntoControl) {
        jdbcTemplate.update("DELETE FROM Voluntario_PuntoControl WHERE id_voluntario = ? AND id_punto_control = ?",
                idVoluntario, idPuntoControl);
    }

    public VoluntarioPuntoControl getVoluntarioPuntoControl(int idVoluntario, int idPuntoControl) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Voluntario_PuntoControl WHERE id_voluntario = ? AND id_punto_control = ?",
                    new VoluntarioPuntoControlRowMapper(), idVoluntario, idPuntoControl);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<VoluntarioPuntoControl> getPuntoControles(int idVoluntario) {
        try {
            return jdbcTemplate.query("SELECT id_punto_control FROM voluntario_puntocontrol WHERE id_voluntario = ?",
                    new VoluntarioPuntoControlRowMapper(), idVoluntario);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
    public List<PuntoControl> getPuntoControles2(int idVoluntario) {
        try {

            return jdbcTemplate.query(
                    "SELECT pc.* FROM PuntoControl pc " +
                            "JOIN Voluntario_PuntoControl vpc ON pc.id_punto_control = vpc.id_punto_control " +
                            "WHERE vpc.id_voluntario = ?",
                    new PuntoControlRowMapper(), idVoluntario);
        } catch (Exception e) {

            return new ArrayList<>();
        }
    }
}