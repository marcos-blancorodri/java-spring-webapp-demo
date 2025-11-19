package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class InscripcionDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addInscripcion(Inscripcion inscripcion) {
        jdbcTemplate.update("INSERT INTO Inscripcion VALUES(?, ?, ?, ?, ?, ?)",
                inscripcion.getDniParticipante(), inscripcion.getIdCarrera(), inscripcion.getNumeroCamiseta(),
                inscripcion.getTiempoMeta(), inscripcion.getFecha(), inscripcion.getPago());
    }

    public void deleteInscripcion(String dniParticipante, int idCarrera) {
        jdbcTemplate.update("DELETE FROM Inscripcion WHERE dni_participante = ? AND id_carrera = ?", dniParticipante, idCarrera);
    }

    public void updateInscripcion(Inscripcion inscripcion) {
        jdbcTemplate.update("UPDATE Inscripcion SET numero_camiseta=?, tiempo_meta=?, fecha=?, pago=? WHERE dni_participante=? AND id_carrera=?",
                inscripcion.getNumeroCamiseta(), inscripcion.getTiempoMeta(),
                inscripcion.getFecha(), inscripcion.getPago(), inscripcion.getDniParticipante(), inscripcion.getIdCarrera());
    }

    public Inscripcion getInscripcion(String dniParticipante, int idCarrera) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Inscripcion WHERE dni_participante = ? AND id_carrera = ?",
                    new InscripcionRowMapper(), dniParticipante, idCarrera);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Inscripcion> getInscripciones(int idCarrera) {
        try {
            return jdbcTemplate.query( "SELECT * FROM inscripcion WHERE id_carrera = ?",
                    new InscripcionRowMapper(), idCarrera);
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Inscripcion>();
        }
    }

    public String getDniByNumeroCamisetaAndCarrera(int numeroCamiseta, int idCarrera) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT dni_participante FROM Inscripcion WHERE numero_camiseta = ? AND id_carrera = ?", String.class, numeroCamiseta, idCarrera);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Map<String, Object>> getInscripcionesConNombreParticipante(int idCarrera) {
        String sql = "SELECT " +
                "   i.id_carrera, i.dni_participante, i.numero_camiseta, i.tiempo_meta, i.fecha, i.pago, " +
                "   p.nombre AS nombre_participante " + // ¡Aquí está la magia!
                "FROM " +
                "   inscripcion i " +
                "JOIN " +
                "   participante p ON i.dni_participante = p.dni " +
                "WHERE " +
                "   i.id_carrera = ?";

        return jdbcTemplate.queryForList(sql, idCarrera);
    }
}
