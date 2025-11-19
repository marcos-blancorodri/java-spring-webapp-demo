package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.Usuario;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class UsuarioDAO {
    private JdbcTemplate jdbcTemplate;
    private final BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addUsuario(Usuario usuario) {
        String encryptedPassword = passwordEncryptor.encryptPassword(usuario.getPassword());
        jdbcTemplate.update("INSERT INTO Usuario(email, password, tipo) VALUES(?, ?, ?)",
                usuario.getEmail(), encryptedPassword, usuario.getTipo());
    }

    public Usuario getUsuario(String email) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Usuario WHERE email=?",
                    new UsuarioRowMapper(), email);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Usuario validateLogin(String email, String rawPassword) {
        Usuario user = getUsuario(email);
        if (user == null) return null;

        if (passwordEncryptor.checkPassword(rawPassword, user.getPassword())) {
            return user;
        } else {
            return null;
        }
    }
}
