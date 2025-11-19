package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.Participante;
import es.uji.ei1027.clubesportiu.model.Posicion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PosicionDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Posicion> getPosiciones() {
        try {
            return jdbcTemplate.query( "SELECT * FROM posicion",
                    new PosicionRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Posicion>();
        }
    }

    public Posicion getPosicion(int idPuntoControl,String dni) {
        try {
            return jdbcTemplate.queryForObject( "SELECT * FROM posicion Where id_punto_control = ? and dni = ?",
                    new PosicionRowMapper(), idPuntoControl, dni);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Posicion> getPosiciones2(int idPuntoControl) {
        try {
            return jdbcTemplate.query( "SELECT * FROM posicion WHERE id_punto_control = ?",
                    new PosicionRowMapper(),
                    idPuntoControl);
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Posicion>();
        }
    }

    public void addPosicion(Posicion posicion) {
        jdbcTemplate.update("INSERT INTO Posicion VALUES(?, ?, ?)",
                posicion.getDni(),posicion.getPosicion(),posicion.getId_punto_control());
    }

    public void deletePosicion(String dni,int idPuntoControl) {
        jdbcTemplate.update("DELETE FROM Posicion WHERE dni = ? AND id_punto_control = ?", dni,idPuntoControl);
    }

    public void updatePosicion(Posicion posicion) {
        jdbcTemplate.update("UPDATE Posicion SET posicion=? WHERE dni=? and id_punto_control = ?",
                posicion.getPosicion(),posicion.getDni(),posicion.getId_punto_control());
    }
}
