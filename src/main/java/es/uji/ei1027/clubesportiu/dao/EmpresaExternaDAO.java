package es.uji.ei1027.clubesportiu.dao;


import es.uji.ei1027.clubesportiu.dao.EmpresaExternaRowMapper;
import es.uji.ei1027.clubesportiu.model.EmpresaExterna;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class EmpresaExternaDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addEmpresaExterna(EmpresaExterna empresaExterna) {
        jdbcTemplate.update("INSERT INTO EmpresaExterna VALUES(?, ?, ?)",
                empresaExterna.getNombre(), empresaExterna.getNif(), empresaExterna.getNombre_promotor());
    }

    public void deleteEmpresaExterna(String nombre) {
        jdbcTemplate.update("DELETE FROM EmpresaExterna WHERE nombre = ?", nombre);
    }

    public void updateEmpresaExterna(EmpresaExterna empresaExterna) {
        jdbcTemplate.update("UPDATE EmpresaExterna SET nif=? nombre_promotor=? WHERE nombre=?",
                empresaExterna.getNif(), empresaExterna.getNombre_promotor(), empresaExterna.getNombre());
    }

    public EmpresaExterna getEmpresaExterna(String nombre) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM EmpresaExterna WHERE nombre = ?", new EmpresaExternaRowMapper(), nombre);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}

