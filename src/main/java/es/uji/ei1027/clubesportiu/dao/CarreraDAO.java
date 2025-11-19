package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.Carrera;
import es.uji.ei1027.clubesportiu.model.Participante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CarreraDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addCarrera(Carrera carrera) {
        jdbcTemplate.update("INSERT INTO carrera (nombre, longitud, fecha, id_competicion, precio) VALUES (?, ?, ?, ?, ?)",
                carrera.getNombre(), carrera.getLongitud(), carrera.getFecha(), carrera.getIdCompeticion(), carrera.getPrecio());
    }


    public void deleteCarrera(Carrera carrera) {
        jdbcTemplate.update("DELETE from carrera where id_carrera=?",
                carrera.getIdCarrera());
    }

    public void deleteCarrera(int carrera) {
        System.out.println(carrera);
        jdbcTemplate.update("DELETE from carrera where id_carrera=?",
                carrera);
    }

    public void updateCarrera(Carrera carrera) {
        jdbcTemplate.update("UPDATE carrera SET nombre=?, longitud=?, fecha=?, precio =? where id_carrera=?",
                carrera.getNombre(), carrera.getLongitud(), carrera.getFecha(), carrera.getPrecio(), carrera.getIdCarrera());
    }

    /* Obté la classificacio amb el nom donat. Torna null si no existeix. */
    public Carrera getCarrera(int idCarrera) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from carrera WHERE id_carrera=?",
                    new CarreraRowMapper(), idCarrera);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Carrera> getCarreras() {
        try {
            return jdbcTemplate.query( "SELECT * FROM carrera",
                    new CarreraRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Carrera>();
        }
    }
    public List<Carrera> getCarrerasByCompeticion(int idCompeticion) {
        return jdbcTemplate.query("SELECT * FROM carrera WHERE id_competicion = ?",
                new CarreraRowMapper(), idCompeticion);
    }






}
