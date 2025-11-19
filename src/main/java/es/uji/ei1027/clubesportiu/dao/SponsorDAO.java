package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SponsorDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addSponsor(Sponsor sponsor) {
        jdbcTemplate.update("INSERT INTO Sponsor (cif, nombre) VALUES(?, ?)",
                sponsor.getCif(), sponsor.getNombre());
    }

    public void deleteSponsor(String cif) {
        jdbcTemplate.update("DELETE FROM Sponsor WHERE cif = ?", cif);
    }

    public void updateSponsor(Sponsor sponsor) {
        jdbcTemplate.update("UPDATE Sponsor SET nombre=? WHERE cif=?",
                sponsor.getNombre(), sponsor.getCif());
    }

    public Sponsor getSponsor(String cif) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Sponsor WHERE cif = ?",
                    new SponsorRowMapper(), cif);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Sponsor> getSponsors() {
        try {
            return jdbcTemplate.query("SELECT * FROM Sponsor",
                    new SponsorRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
}
