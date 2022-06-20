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
import ru.yandex.practicum.filmorate.exception.MpaNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.GenreDao;
import ru.yandex.practicum.filmorate.storage.film.LikesDao;
import ru.yandex.practicum.filmorate.storage.film.MpaDao;

import java.sql.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

@Component
@Primary
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private final MpaDao mpaDao;
    private final GenreDao genreDao;
    private final LikesDao likesDao;


    @Override
    public Film findById(int id) throws FilmNotFoundException {
        String sql = "SELECT * FROM FILMS WHERE FILM_ID = ?";
        Optional<Film> film = jdbcTemplate.query(sql, (rs, rowNum) -> {
            try {
                return createFilm(rs);
            } catch (MpaNotFoundException e) {
                throw new RuntimeException(e);
            }
        }, id).stream().findAny();
        if (film.isPresent()) {
            return film.get();
        } else {
            throw new FilmNotFoundException("Ползователь не найден.", "id", String.valueOf(id));
        }
    }

    private Film createFilm(ResultSet rs) throws SQLException, MpaNotFoundException {
        int id = rs.getInt("FILM_ID");
        String name = rs.getString("NAME");
        String description = rs.getString("DESCRIPTION");
        LocalDate releaseDate = rs.getDate("RELEASEDATE").toLocalDate();
        Integer rate = rs.getInt("RATE");
        Integer duration = rs.getInt("DURATION");
        Mpa mpa = mpaDao.findMpaById(rs.getInt("MPA_ID"));
        TreeSet<Genre> genresSet = genreDao.findGenreFilm(id);
        Set<Integer> likes = likesDao.findFilmLikes(id);


        return new Film(id, name, description, releaseDate, duration, rate, likes, mpa, genresSet);
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
                film.getGenres().stream().map(Genre::getId).forEach(r -> genreDao.addFilmToGenre(film.getId(), r));
            }
            return film;
        } else {
            throw new FilmAlreadyExistsException("Создание нового фильма с id запрещено", "id",
                    String.valueOf(film.getId()));
        }
    }

    @Override
    public Film update(Film film) throws FilmNotFoundException, MpaNotFoundException {
        this.findById(film.getId());
        String sql = "UPDATE FILMS SET NAME =?, DESCRIPTION =?, RELEASEDATE=?, RATE=?, DURATION=?,MPA_ID=? WHERE FILM_ID=?";
        jdbcTemplate.update(sql, ps -> {
            ps.setString(1, film.getName());
            ps.setString(2, film.getDescription());
            ps.setDate(3, Date.valueOf(film.getReleaseDate()));
            ps.setInt(4, film.getRate());
            ps.setInt(5, film.getDuration());
            ps.setInt(6, film.getMpa().getId());
            ps.setInt(7, film.getId());
        });
        if (film.getGenres() != null) {
            Set<Genre> oldGenres = genreDao.findGenreFilm(film.getId());
            if (oldGenres!=null && !oldGenres.equals(film.getGenres())) {
                oldGenres.forEach(r -> genreDao.deleteFilmToGenre(film.getId(), r.getId()));
                film.getGenres().forEach(r -> genreDao.addFilmToGenre(film.getId(), r.getId()));
            } else {
                film.getGenres().forEach(r -> genreDao.addFilmToGenre(film.getId(), r.getId()));
            }
        }
        return film;
    }

    @Override
    public void delete(int id) {

    }


    @Override
    public Collection<Film> findAll() {
        String sql = "SELECT * FROM FILMS";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            try {
                return createFilm(rs);
            } catch (MpaNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public Collection<Film> findPopularFilm(Integer count) {
        String sql = "SELECT * FROM FILMS ORDER BY RATE  LIMIT ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            try {
                return createFilm(rs);
            } catch (MpaNotFoundException e) {
                throw new RuntimeException(e);
            }
        }, count);
    }
}
