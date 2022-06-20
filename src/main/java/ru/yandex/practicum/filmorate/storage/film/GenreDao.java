package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.exception.GenreNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

public interface GenreDao {
    Genre findGenreById(Integer genreId) throws GenreNotFoundException;
    Collection<Genre> findAllGenre();
    Set<Genre> findGenreFilm(Integer filmId); // TODO: 20.06.2022 Пусть все таки тут будет Set или Collection, детали реализации, конкретную реализацию коллекции, лучше спрятать, особенно в интерфейсе :) 
    void addFilmToGenre(Integer filmId,Integer genreId);
    void deleteFilmToGenre(Integer filmId, Integer genreId);
}
