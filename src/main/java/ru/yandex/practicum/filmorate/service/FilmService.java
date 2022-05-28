package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

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
        log.info("Пользователь id {} добавил лайк фильму id {}",userId,id);
    }

    public void deleteLike(int id, int userId) {
        Film film = filmStorage.findById(id);
        if (film.getLikeSet().contains(userId)) {
            film.getLikeSet().remove(userId);
            log.info("Пользователь id {} удалил лайк фильму id {}",userId,id);
        } else {
            throw new FilmNotFoundException("Лайк фильма не найден","Id",String.valueOf(id));
        }
    }

    public Collection<Film> findPopularFilm(int count) {
        log.info("Передан список из {} популярных фильмов", count);
        return filmStorage.findAll().stream().sorted(Comparator.comparingInt(r -> -r.getLikeSet().size()))
                .limit(count).collect(Collectors.toList());
    }

    public Film addFilm(Film film) {
        filmStorage.addFilm(film);
        return film;
    }
    public Film updateFilm(Film film){
        return filmStorage.update(film);
    }
    public Collection<Film> findAll(){
        log.info("Передан список всех фильмов");
     return filmStorage.findAll();
    }

    public Film findById(int id) {
        return filmStorage.findById(id);
    }
}
