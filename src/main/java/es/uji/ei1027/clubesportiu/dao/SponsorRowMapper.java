package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.Sponsor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class SponsorRowMapper implements RowMapper<Sponsor> {
    @Override
    public Sponsor mapRow(ResultSet rs, int rowNum) throws SQLException {
        Sponsor sponsor = new Sponsor();
        sponsor.setNombre(rs.getString("nombre"));
        sponsor.setCif(rs.getString("cif"));
        return sponsor;
    }
}
