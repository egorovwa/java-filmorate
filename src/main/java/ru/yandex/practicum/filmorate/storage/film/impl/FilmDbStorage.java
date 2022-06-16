package ru.yandex.practicum.filmorate.storage.film.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmAlreadyExistsException;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.sql.*;
import java.util.Collection;

@Component
@Primary

public class FilmDbStorage implements FilmStorage {
    JdbcTemplate jdbcTemplate;

    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film findById(int id) throws FilmNotFoundException {


        return null;
    }

    @Override
    public Film addFilm(Film film) throws FilmAlreadyExistsException {
        if (film.getId() == null) {
            String sql = "INSERT INTO FILMS(NAME,DESCRIPTION,RELEASEDATE,DURATION,MPA_ID)" +
                    "VALUES ( ?,?,?,?,? )";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, film.getName());
                    ps.setString(2, film.getDescription());
                    ps.setDate(3, Date.valueOf(film.getReleaseDate()));
                    ps.setInt(4, film.getDuration());
                    ps.setInt(5, film.getMpa().getId());
                    return ps;
                }
            }, keyHolder);
            film.setId(keyHolder.getKey().intValue());
            if (film.getGenres() != null) {
                film.getGenres().stream().map(Genre::getId).forEach(r -> addFilmGenres(film.getId(), r));
            }
            return film;
        } else {
            throw new FilmAlreadyExistsException("Создание нового фильма с id запрещено", "id",
                    String.valueOf(film.getId()));
        }
    }

    private void addFilmGenres(Integer filmId, Integer genreId) {
        String sql = "INSERT INTO FILM_GENRES(GENRES_ID, FILM_ID) VALUES ( ?,? ) ";
        jdbcTemplate.update(sql, ps -> {
            ps.setInt(1, genreId);
            ps.setInt(2, filmId);
        });
    }

    @Override
    public Film update(Film film) throws FilmNotFoundException {
        return null;
    }

    @Override
    public void delete(int id) throws FilmNotFoundException {

    }

    @Override
    public Collection<Film> findAll() {
        return null;
    }
}
