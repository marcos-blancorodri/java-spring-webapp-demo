package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.Carrera;
import es.uji.ei1027.clubesportiu.model.Club;
import es.uji.ei1027.clubesportiu.model.Participante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ParticipanteDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addParticipante(Participante participante) {
        jdbcTemplate.update("INSERT INTO Participante VALUES(?, ?, ?, ?, ?, ?)",
                participante.getDni(), participante.getNombre(), participante.getFecha_nacimiento(),
                participante.getGenero(), participante.getEmail(), participante.getNum_federado());
    }

    public void deleteParticipante(String dni) {
        jdbcTemplate.update("DELETE FROM Participante WHERE dni = ?", dni);
    }

    public void updateParticipante(Participante participante) {
        jdbcTemplate.update("UPDATE Participante SET nombre=?, fecha_nacimiento=?, genero=?, email=?, num_federado=? WHERE dni=?",
                participante.getNombre(), participante.getFecha_nacimiento(), participante.getGenero(),
                participante.getEmail(),participante.getNum_federado(), participante.getDni());
    }

    public Participante getParticipante(String dni) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Participante WHERE dni = ?", new ParticipanteRowMapper(), dni);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Participante> getParticipantes() {
        try {
            return jdbcTemplate.query( "SELECT * FROM Participante",
                    new ParticipanteRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Participante>();
        }
    }

    public String getDNI(String email) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Participante WHERE email = ?", new ParticipanteRowMapper(), email).getDni();
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
