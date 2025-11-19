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
public class PuntoControlDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addPuntoControl(PuntoControl puntoControl) {
        jdbcTemplate.update("INSERT INTO PuntoControl(id_carrera, nombre_puntocontrol) VALUES(?, ?)",
                puntoControl.getIdCarrera(), puntoControl.getNombrePuntoControl());
    }

    public void deletePuntoControl(int idPuntoControl) {
        jdbcTemplate.update("DELETE FROM PuntoControl WHERE id_punto_control = ?", idPuntoControl);
    }

    public void updatePuntoControl(PuntoControl puntoControl) {
        jdbcTemplate.update("UPDATE PuntoControl SET id_carrera=?, nombre_puntocontrol=? WHERE id_punto_control=?",
                puntoControl.getIdCarrera(), puntoControl.getNombrePuntoControl(), puntoControl.getIdPuntoControl());
    }

    public PuntoControl getPuntoControl(int idPuntoControl) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM PuntoControl WHERE id_punto_control = ?",
                    new PuntoControlRowMapper(), idPuntoControl);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }



    public List<PuntoControl> getPuntosControl() {
        try {
            return jdbcTemplate.query( "SELECT * FROM PuntoControl",
                    new PuntoControlRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<PuntoControl>();
        }
    }

    public List<PuntoControl> getPuntosControlCarrera(int idCarrera) {
        try {
            return jdbcTemplate.query( "SELECT * FROM PuntoControl Where id_carrera = ?",
                    new PuntoControlRowMapper(),idCarrera);
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<PuntoControl>();
        }
    }

    public List<PuntoControl> getPuntosControlDeCarreras(List<Integer> idsCarrera) {
        if (idsCarrera == null || idsCarrera.isEmpty()) {
            return new ArrayList<>();
        }

        List<PuntoControl> todosLosPuntos = new ArrayList<>();

        try {

            for (Integer idCarrera : idsCarrera) {

                List<PuntoControl> puntosDeUnaCarrera = jdbcTemplate.query(
                        "SELECT * FROM PuntoControl WHERE id_carrera = ?",
                        new PuntoControlRowMapper(),
                        idCarrera
                );
                todosLosPuntos.addAll(puntosDeUnaCarrera);
            }
        } catch (Exception e) {

        }

        return todosLosPuntos;
    }

}