package ru.yandex.practicum.filmorate.storage.film.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.MpaNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.film.MpaDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

@Component
public class MpaDaoImpl implements MpaDao {
    JdbcTemplate jdbcTemplate;

    public MpaDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Mpa findMpaById(Integer mpaId) throws MpaNotFoundException {
        String sql = "SELECT * FROM MPAS WHERE MPA_ID = ?";
        Optional<Mpa> mpa = jdbcTemplate.query(sql, (rs, rowNum) -> mpaCreate(rs), mpaId).stream().findAny();
        if (mpa.isPresent()) {
            return mpa.get();
        } else {
            throw new MpaNotFoundException("MPA не найден", "mpa id", String.valueOf(mpaId));
        }
    }

    @Override
    public Collection<Mpa> findAllMpa() {
        String sql ="SELECT * FROM MPAS";
        return jdbcTemplate.query(sql,(rs, rowNum) -> mpaCreate(rs));
    }

    private Mpa mpaCreate(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("MPA_ID");
        String name = rs.getString("MPA_NAME");
        String description = rs.getString("MPA_DESCRIPTION");
        return new Mpa(id, name, description);
    }
}
