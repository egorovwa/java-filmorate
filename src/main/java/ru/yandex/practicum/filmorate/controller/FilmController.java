package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.net.http.HttpRequest;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class FilmController {
    public static final LocalDate MOVIE_CREATION_DAY = LocalDate.of(1895, 12, 28);
    int id = 0;
    Map<Integer, Film> filmMap;


    public FilmController() {
        filmMap = new HashMap<>();
    }

    @PostMapping("/films")
    public Film addFilm(@Valid @RequestBody Film film) {
        filmMap.put(film.getId(), film);
        log.info("Добавлен фильм: " + film.getName() + " всего фильмов: " + filmMap.size());
        return film;
    }
}
