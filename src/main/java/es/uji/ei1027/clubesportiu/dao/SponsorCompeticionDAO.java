package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.Sponsor;
import es.uji.ei1027.clubesportiu.model.SponsorCompeticion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

@Repository
public class SponsorCompeticionDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addSponsorCompeticion(SponsorCompeticion sponsorCompeticion) {
        jdbcTemplate.update("INSERT INTO Sponsor_Competicion VALUES(?, ?)",
                sponsorCompeticion.getIdCompeticion(), sponsorCompeticion.getCifSponsor());
    }

    public void deleteSponsorCompeticion(int idCompeticion, String cifSponsor) {
        jdbcTemplate.update("DELETE FROM Sponsor_Competicion WHERE id_competicion = ? AND cif_sponsor = ?",
                idCompeticion, cifSponsor);
    }

    public SponsorCompeticion getSponsorCompeticion(int idCompeticion, String cifSponsor) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Sponsor_Competicion WHERE id_competicion = ? AND cif_sponsor = ?",
                    new SponsorCompeticionRowMapper(), idCompeticion, cifSponsor);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Map<String, Object>> getSponsorsInfoDeCompeticion(int idCompeticion) {
        String sql = "SELECT s.nombre, s.cif FROM Sponsor s " +
                "JOIN Sponsor_Competicion sc ON s.cif = sc.cif_sponsor " +
                "WHERE sc.id_competicion = ?";
        try {
            return jdbcTemplate.queryForList(sql, idCompeticion);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }


    public List<Sponsor> getSponsorsNoAsignadosACompeticion(int idCompeticion) {
        String sql = "SELECT * FROM Sponsor WHERE cif NOT IN " +
                "(SELECT cif_sponsor FROM Sponsor_Competicion WHERE id_competicion = ?)";
        try {
            return jdbcTemplate.query(sql, new SponsorRowMapper(), idCompeticion);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
}
