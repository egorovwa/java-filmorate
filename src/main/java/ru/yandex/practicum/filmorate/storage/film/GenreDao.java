package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.exception.GenreNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

public interface GenreDao {
    Genre findGenreById(Integer genreId) throws GenreNotFoundException;
    Collection<Genre> findAllGenre();
}
