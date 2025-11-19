package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.Promotor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class PromotorRowMapper implements RowMapper<Promotor> {
    @Override
    public Promotor mapRow(ResultSet rs, int rowNum) throws SQLException {
        Promotor promotor = new Promotor();
        promotor.setNombre(rs.getString("nombre"));
        promotor.setTaxId(rs.getString("taxid"));
        promotor.setEmail(rs.getString("email"));
        return promotor;
    }
}
