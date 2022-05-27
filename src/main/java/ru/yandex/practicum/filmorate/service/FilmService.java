package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {
    FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public void addLike(int id, int userId) {
        Film film = filmStorage.findById(id);
        film.getLikeSet().add(userId);
    }

    public void deleteLike(int id, int userId) {
        Film film = filmStorage.findById(id);
        if (film.getLikeSet().contains(id)) {
            film.getLikeSet().remove(id);
        } else {
            throw new FilmNotFoundException("Лайк фильма не найден","Id",String.valueOf(id));
        }
    }

    public Collection<Film> findPopularFilm(int count) {
        return filmStorage.findAll().stream().sorted(Comparator.comparingInt(r -> r.getLikeSet().size()))
                .limit(count).collect(Collectors.toList());
    }

    public Film addFilm(Film film) {
        filmStorage.addFilm(film);
        log.info("Добавлен фильм: {}", film.getName());
        return film;
    }
    public Film updateFilm(Film film){
        return filmStorage.update(film);
    }
    public Collection<Film> findAll(){
     return filmStorage.findAll();
    }

    public Film findById(int id) {
        return filmStorage.findById(id);
    }
}
