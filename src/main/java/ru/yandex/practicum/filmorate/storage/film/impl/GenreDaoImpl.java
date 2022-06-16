package ru.yandex.practicum.filmorate.storage.film.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.GenreNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.film.GenreDao;

import javax.sql.RowSet;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

@Component
public class GenreDaoImpl implements GenreDao {
    JdbcTemplate jdbcTemplate;

    public GenreDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Genre findGenreById(Integer genreId) throws GenreNotFoundException {
        String sql = "SELECT * FROM GENRES WHERE GENRES_ID =?";
        Optional<Genre> genre = jdbcTemplate.query(sql, (rs, rowNum) -> createGenre(rs), genreId).stream().findAny();
        if (genre.isPresent()) {
            return genre.get();
        } else {
            throw new GenreNotFoundException("Жанр не найден", "id", String.valueOf(genreId));
        }

    }

    @Override
    public Collection<Genre> findAllGenre() {
        String sql = "SELECT * FROM GENRES";
        return jdbcTemplate.query(sql,(rs, rowNum) -> createGenre(rs));
    }

    private Genre createGenre(ResultSet rs) throws SQLException {
        int id = rs.getInt("GENRES_ID");
        String name = rs.getString("GENRE_NAME");
        return new Genre(id, name);
    }
}

