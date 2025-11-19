package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.EmpresaExterna;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class EmpresaExternaRowMapper implements RowMapper<EmpresaExterna> {
    @Override
    public EmpresaExterna mapRow(ResultSet rs, int rowNum) throws SQLException {
        EmpresaExterna empresaExterna = new EmpresaExterna();
        empresaExterna.setNombre(rs.getString("nombre"));
        empresaExterna.setNif(rs.getString("nif"));
        empresaExterna.setNombre_promotor(rs.getString("nombre_promotor"));
        return empresaExterna;
    }
}
