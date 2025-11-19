package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class PromotorDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addPromotor(Promotor promotor) {
        jdbcTemplate.update("INSERT INTO Promotor VALUES(?, ?, ?)",
                promotor.getNombre(), promotor.getTaxId(), promotor.getEmail());
    }

    public void deletePromotor(String nombre) {
        jdbcTemplate.update("DELETE FROM Promotor WHERE nombre = ?", nombre);
    }

    public void updatePromotor(Promotor promotor) {
        jdbcTemplate.update("UPDATE Promotor SET taxid=? email=? WHERE nombre=?",
                promotor.getTaxId(), promotor.getEmail(), promotor.getNombre());
    }

    public Promotor getPromotor(String email) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Promotor WHERE email = ?",
                    new PromotorRowMapper(), email);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


}