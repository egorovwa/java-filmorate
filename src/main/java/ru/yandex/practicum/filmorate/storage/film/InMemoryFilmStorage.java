package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmAlreadyExistsException;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    Map<Integer, Film> filmMap = new HashMap<>();
    int id = 1;

    @Override
    public Film findById(int id) {
        if (filmMap.containsKey(id)) {
            return filmMap.get(id);
        } else {
            throw new FilmNotFoundException("Фильм не найден.", "id", String.valueOf(id));
        }

    }

    @Override
    public Film addFilm(Film film) {
        if (film.getId() == null) {
            film.setId(id);
            filmMap.put(film.getId(), film);
            id++;
            return film;
        } else if (!filmMap.containsKey(film.getId())) {
            filmMap.put(film.getId(), film);
            return film;
        } else {
            throw new FilmAlreadyExistsException("Фильм уже существует", "id", String.valueOf(film.getId()));
        }

    }

    @Override
    public Film update(Film film) {
        if (film.getId() != null && filmMap.containsKey(film.getId())) {
            filmMap.put(film.getId(), film);
        } else {
            throw new FilmNotFoundException("Фильм не найден.", "id", String.valueOf(film.getId()));
        }
        return film;
    }

    @Override
    public void delete(int id) {
        if (filmMap.containsKey(id)) {
            filmMap.remove(id);
        } else {
            throw new FilmNotFoundException("Фильм не найден.", "id", String.valueOf(id));
        }

    }

    @Override
    public Collection<Film> findAll() {
        return Collections.unmodifiableCollection(filmMap.values());
    }
}
