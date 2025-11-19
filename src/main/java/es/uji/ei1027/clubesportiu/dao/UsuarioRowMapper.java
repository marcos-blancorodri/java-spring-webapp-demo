package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.Usuario;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioRowMapper implements RowMapper<Usuario> {
    @Override
    public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setEmail(rs.getString("email"));
        usuario.setPassword(rs.getString("password")); // la contraseña ya está cifrada
        usuario.setTipo(rs.getString("tipo"));
        return usuario;
    }
}
