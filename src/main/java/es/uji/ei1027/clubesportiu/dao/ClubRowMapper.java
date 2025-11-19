package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.Carrera;
import es.uji.ei1027.clubesportiu.model.Club;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class ClubRowMapper implements
        RowMapper<Club> {

    public Club mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        Club club = new Club();
        club.setCodigo(rs.getString("codigo"));
        club.setNombre(rs.getString("nombre"));
        club.setPoblacion(rs.getString("poblacion"));
        club.setPais(rs.getString("pais"));
        club.setPersona_contacto(rs.getString("persona_contacto"));
        club.setCorreo_contacto(rs.getString("correo_contacto"));
        club.setNif(rs.getString("nif"));
        club.setNombre_promotor(rs.getString("nombre_promotor"));
        return club;
    }
}