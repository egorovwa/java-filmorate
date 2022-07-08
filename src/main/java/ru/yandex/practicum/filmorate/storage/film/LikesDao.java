package ru.yandex.practicum.filmorate.storage.film;

import java.util.Set;

public interface LikesDao {
   void addLike(Integer userId, Integer filmId);
   void deleteLike(Integer userId, Integer filmId);
   Set<Integer> findFilmLikes(Integer filmId);

}
