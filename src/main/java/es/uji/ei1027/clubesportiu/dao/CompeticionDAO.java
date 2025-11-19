package es.uji.ei1027.clubesportiu.dao;


import es.uji.ei1027.clubesportiu.model.Club;
import es.uji.ei1027.clubesportiu.model.Competicion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CompeticionDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addCompeticion(Competicion competicion) {
        jdbcTemplate.update("INSERT INTO competicion (nombre, fecha_ini, fecha_fin, descripcion, nombre_promotor) VALUES (?, ?, ?, ?, ?)",
                competicion.getNombre(), competicion.getFecha_in(), competicion.getFecha_fin(), competicion.getDescripcion(), competicion.getNombre_promotor());
    }

    public void deleteCompeticion(Competicion competicion) {
        jdbcTemplate.update("DELETE from competicion where id_competicion=?",
                competicion.getIdCompeticion());
    }

    public void deleteCompeticion(int idCompeticion) {
        jdbcTemplate.update("DELETE from competicion where id_competicion=?",
                idCompeticion);
    }

    public void updateCompeticion(Competicion competicion) {
        jdbcTemplate.update("UPDATE competicion SET nombre=?, fecha_ini=?, fecha_fin=?, descripcion=?, nombre_promotor=? where id_competicion=?",
                competicion.getNombre(), competicion.getFecha_in(), competicion.getFecha_fin(), competicion.getDescripcion(), competicion.getNombre_promotor(), competicion.getIdCompeticion());
    }

    /* Obté la classificacio amb el nom donat. Torna null si no existeix. */
    public Competicion getCompeticion(int idCompeticion) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from competicion WHERE id_competicion=?",
                    new CompeticionRowMapper(), idCompeticion);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Competicion> getCompeticionesPromotor(String nombre_promotor) {
        try {
            return jdbcTemplate.query("SELECT * FROM competicion WHERE nombre_promotor = ?",
                    new CompeticionRowMapper(), nombre_promotor);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }

    }

    public List<Competicion> getCompeticionesTodas() {
        try {
            return jdbcTemplate.query("SELECT * FROM competicion",
                    new CompeticionRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

}