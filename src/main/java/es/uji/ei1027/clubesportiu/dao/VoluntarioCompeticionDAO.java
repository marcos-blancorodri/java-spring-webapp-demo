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
public class VoluntarioCompeticionDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addVoluntarioCompeticion(VoluntarioCompeticion voluntarioCompeticion) {
        jdbcTemplate.update("INSERT INTO Voluntario_Competicion VALUES(?, ?)",
                voluntarioCompeticion.getIdVoluntario(), voluntarioCompeticion.getIdCompeticion());
    }

    public void deleteVoluntarioCompeticion(int idVoluntario, int idCompeticion) {
        jdbcTemplate.update("DELETE FROM Voluntario_Competicion WHERE id_voluntario = ? AND id_competicion = ?",
                idVoluntario, idCompeticion);
    }

    public VoluntarioCompeticion getVoluntarioCompeticion(int idVoluntario, int idCompeticion) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Voluntario_Competicion WHERE id_voluntario = ? AND id_competicion = ?",
                    new VoluntarioCompeticionRowMapper(), idVoluntario, idCompeticion);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<VoluntarioCompeticion> getCompeticion(int idVoluntario) {
        try {
            return jdbcTemplate.query("SELECT * FROM Voluntario_Competicion WHERE id_voluntario = ?",
                    new VoluntarioCompeticionRowMapper(), idVoluntario);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<VoluntarioCompeticion>();
        }
    }

    public List<VoluntarioCompeticion> getVoluntarios(int idCompeticion) {
        try {
            return jdbcTemplate.query("SELECT * FROM Voluntario_Competicion WHERE id_competicion = ?",
                    new VoluntarioCompeticionRowMapper(), idCompeticion);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<VoluntarioCompeticion>();
        }
    }

    public void deleteVoluntarioCompeticion2(int idVoluntario, int idCompeticion) {
        jdbcTemplate.update("DELETE FROM voluntario_competicion WHERE id_voluntario = ? AND id_competicion = ?",
                idVoluntario, idCompeticion);
    }

}