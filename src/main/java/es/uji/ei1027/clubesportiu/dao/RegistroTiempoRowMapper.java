package es.uji.ei1027.clubesportiu.dao;
import java.time.LocalTime;

import es.uji.ei1027.clubesportiu.model.RegistroTiempo;
import es.uji.ei1027.clubesportiu.model.SolicitudPrevia;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class RegistroTiempoRowMapper implements RowMapper<RegistroTiempo> {
    @Override
    public RegistroTiempo mapRow(ResultSet rs, int rowNum) throws SQLException {
        RegistroTiempo registroTiempo = new RegistroTiempo();
        registroTiempo.setIdRegistro(rs.getInt("id_registro"));
        registroTiempo.setTiempo(rs.getObject("tiempo", LocalTime.class));
        registroTiempo.setComentario(rs.getString("comentarios"));
        registroTiempo.setIdCarrera(rs.getInt("id_carrera"));
        registroTiempo.setDniParticipante(rs.getString("dni_participante"));
        registroTiempo.setPunto_control(rs.getInt("id_punto_control"));
        return registroTiempo;
    }
}
