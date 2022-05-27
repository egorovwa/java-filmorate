package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@Slf4j
public class FilmController {
    public static final int DEFAULT_COUNT = 10;
    FilmService filmService;


    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;

    }

    @PostMapping("/films")
    public Film addFilm(@Valid @RequestBody Film film) {
        return filmService.addFilm(film);
    }

    @PutMapping("/films")
    public Film update(@Valid @RequestBody Film film) {
        return filmService.updateFilm(film);
    }

    @GetMapping("/films")
    public Collection<Film> findAll() {
        return filmService.findAll();
    }

    @PutMapping("/films/{id}/like/{userId}")
    public void addLike(@PathVariable int id, @PathVariable int userId) {
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void deleteLike(@PathVariable int id, @PathVariable int userId) {
        filmService.deleteLike(id, userId);
    }

    @GetMapping("/films/popular")
    public Collection<Film> findPopularFilm(@RequestParam Optional<Integer> mayBeCount) {
        if (mayBeCount.isPresent()) {
            if (mayBeCount.get() > 0) {
                return filmService.findPopularFilm(mayBeCount.get());
            } else {
                throw new IllegalArgumentException("count должен быть больше 0.");
            }
        } else return filmService.findPopularFilm(DEFAULT_COUNT);

    }
}
