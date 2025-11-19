package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class VoluntarioDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addVoluntario(Voluntario voluntario) {
        jdbcTemplate.update("INSERT INTO voluntario (nombre, fecha_nacimiento, genero, email) VALUES(?, ?, ?, ?)",
                voluntario.getNombre(),
                voluntario.getFecha_nacimiento(),
                voluntario.getGenero(),
                voluntario.getEmail());
    }

    public void deleteVoluntario(int idVoluntario) {
        jdbcTemplate.update("DELETE FROM Voluntario WHERE id_voluntario=?", idVoluntario);
    }

    public void updateVoluntario(Voluntario voluntario) {
        jdbcTemplate.update("UPDATE Voluntario SET nombre=?, fecha_nacimiento=?, genero=?, email=? WHERE id_voluntario=?",
                voluntario.getNombre(), voluntario.getFecha_nacimiento(), voluntario.getGenero(), voluntario.getEmail(), voluntario.getIdVoluntario());
    }

    public Voluntario getVoluntario(int idVoluntario) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Voluntario WHERE id_voluntario=?", new VoluntarioRowMapper(), idVoluntario);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Voluntario> getVoluntarios() {
        try {
            return jdbcTemplate.query("SELECT * FROM Voluntario", new VoluntarioRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    public Voluntario getVoluntarioPorEmail(String email) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM voluntario WHERE email = ?",
                    new VoluntarioRowMapper(), email);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Voluntario> getVoluntariosDeCompeticion(int idCompeticion) {
        return jdbcTemplate.query(
                "SELECT v.* FROM voluntario v " +
                        "JOIN voluntario_competicion vc ON v.id_voluntario = vc.id_voluntario " +
                        "WHERE vc.id_competicion = ?",
                new VoluntarioRowMapper(),
                idCompeticion
        );
    }
}