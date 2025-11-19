package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class FichaClubDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addFichaClub(FichaClub fichaClub) {
        jdbcTemplate.update("INSERT INTO FichaClub VALUES(?, ?, ?, ?, ?, ?)",
                fichaClub.getNum_federado(), fichaClub.getNombre(), fichaClub.getValidez_fecha(), fichaClub.getCodigo_club(), fichaClub.getAnyo(), fichaClub.getDni_participante());
    }

    public void deleteFichaClub(String numFederado, int anyo) {
        jdbcTemplate.update("DELETE FROM FichaClub WHERE num_federado=? AND anyo=?", numFederado, anyo);
    }

    public void updateFichaClub(FichaClub fichaClub) {
        jdbcTemplate.update("UPDATE FichaClub SET nombre=?, validez_fecha=?, codigo_club=? WHERE num_federado=? AND anyo=?",
                fichaClub.getNombre(), fichaClub.getValidez_fecha(), fichaClub.getCodigo_club(), fichaClub.getNum_federado(), fichaClub.getAnyo());
    }

    public FichaClub getFichaClub(String numFederado, int anyo) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM FichaClub WHERE num_federado=? AND anyo=?", new FichaClubRowMapper(), numFederado, anyo);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}