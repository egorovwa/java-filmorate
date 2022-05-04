package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@Slf4j
public class FilmController {
    int id = 0;
   private final Map<Integer, Film> filmMap;
    public FilmController() {
        filmMap = new ConcurrentHashMap<>();

    }

    @PostMapping("/films")
    public Film addFilm(@Valid @RequestBody Film film) {
        film.setId(id);
        id++;
        filmMap.put(film.getId(), film);
        log.info("Добавлен фильм: {} всего фильмов: {}" ,film.getName() ,filmMap.size());
        return film;
    }

    @PutMapping("/films")
    public Film update(@Valid @RequestBody Film film) {
        if (film.getId() != null && filmMap.containsKey(film.getId())) {
            filmMap.put(film.getId(), film);
            log.info("Обновлен фильм: {} всего фильмов: {}" ,film.getName() ,filmMap.size());
        } else {
            film.setId(id);
            id++;
            filmMap.put(film.getId(), film);
            log.info("Добавлен фильм: {} всего фильмов: {}" ,film.getName() ,filmMap.size());
        }
        return film;
    }

    @GetMapping("/films")
    public Collection<Film> findAll() {
        log.trace("Передан список всех фильмов");
        return Collections.unmodifiableCollection(filmMap.values());
    }

}
