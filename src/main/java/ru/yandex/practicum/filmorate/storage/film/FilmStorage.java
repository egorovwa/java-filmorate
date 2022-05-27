package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {
   Film findById(int id);
   Film addFilm(Film film);
   Film update(Film film);
   void delete(int id);
   Collection<Film> findAll();


}

