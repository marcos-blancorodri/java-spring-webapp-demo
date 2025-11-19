package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.Carrera;
import es.uji.ei1027.clubesportiu.model.Club;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class ClubDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addClub(Club club) {
        jdbcTemplate.update("INSERT INTO club VALUES(?, ?, ?, ?, ?, ?, ?, ?)",
                club.getCodigo(), club.getNombre(), club.getPoblacion(), club.getPais(), club.getPersona_contacto(),club.getCorreo_contacto(), club.getNif(), club.getNombre_promotor());
    }

    public void deleteClub(Club club) {
        jdbcTemplate.update("DELETE from club where codigo=?",
                club.getCodigo());
    }

    public void updateClub(Club club) {
        jdbcTemplate.update("UPDATE carrera SET nombre=?, poblacion=?, pais=?, persona_contacto=?, correo_contacto=?, nif=?, nombre_promotor=? where codigo=?",
                club.getNombre(),club.getPoblacion(),club.getPais(),club.getPersona_contacto(),club.getCorreo_contacto(), club.getNif(), club.getNombre_promotor(), club.getCodigo());
    }

    /* Obté la classificacio amb el nom donat. Torna null si no existeix. */
    public Club getClub(String codigo) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from club WHERE codigo=??",
                    new ClubRowMapper(),codigo);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }
}
