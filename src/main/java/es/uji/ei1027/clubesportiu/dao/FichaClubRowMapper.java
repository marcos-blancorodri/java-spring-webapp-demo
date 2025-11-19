package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.FichaClub;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class FichaClubRowMapper implements RowMapper<FichaClub> {
    @Override
    public FichaClub mapRow(ResultSet rs, int rowNum) throws SQLException {
        FichaClub fichaClub = new FichaClub();
        fichaClub.setNum_federado(rs.getString("num_federado"));
        fichaClub.setNombre(rs.getString("nombre"));
        fichaClub.setValidez_fecha(rs.getObject("validez_fecha", LocalDate.class));
        fichaClub.setCodigo_club(rs.getString("codigo_club"));
        fichaClub.setAnyo(rs.getInt("anyo"));
        fichaClub.setDni_participante(rs.getString("dni_participante"));

        return fichaClub;
    }
}
