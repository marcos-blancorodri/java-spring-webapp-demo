package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.Carrera;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class CarreraRowMapper implements
        RowMapper<Carrera> {

    public Carrera mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        Carrera carrera = new Carrera();
        carrera.setIdCarrera(rs.getInt("id_carrera"));
        carrera.setLongitud(rs.getInt("longitud"));
        carrera.setFecha(rs.getObject("fecha", LocalDate.class));
        carrera.setIdCompeticion(rs.getInt("id_competicion"));
        carrera.setNombre(rs.getString("nombre"));
        carrera.setPrecio(rs.getInt("precio"));
        return carrera;
    }
}
