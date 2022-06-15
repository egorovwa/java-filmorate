package ru.yandex.practicum.filmorate.storage.film.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmAlreadyExistsException;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

@Component
@Primary
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {
    JdbcTemplate jdbcTemplate;
    @Override
    public Film findById(int id) throws FilmNotFoundException {


        return null;
    }

    @Override
    public Film addFilm(Film film) throws FilmAlreadyExistsException {
        return null;
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
