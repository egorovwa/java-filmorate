package ru.yandex.practicum.filmorate.storage.film.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.LikeAlreadyExistsException;
import ru.yandex.practicum.filmorate.storage.film.LikesDao;

import java.util.HashSet;
import java.util.Set;

@Component
public class LikesDaoImpl implements LikesDao {
    JdbcTemplate jdbcTemplate;

    public LikesDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addLike(Integer userId, Integer filmId) {
        String sql = "INSERT INTO LIKES(user_id, film_id) VALUES ( ?,? );" +
                "UPDATE FILMS SET RATE=RATE+1 WHERE FILM_ID=?";
            jdbcTemplate.update(sql,ps -> {
                ps.setInt(1,userId);
                ps.setInt(2,filmId);
                ps.setInt(3,filmId);
            });

    }

    @Override
    public void deleteLike(Integer userId, Integer filmId) {
        String sql ="DELETE FROM LIKES WHERE USER_ID=? AND FILM_ID=?;" +
                "UPDATE FILMS SET RATE=RATE-1 WHERE FILM_ID=?";
        jdbcTemplate.update(sql,ps -> {
            ps.setInt(1,userId);
            ps.setInt(2,filmId);
            ps.setInt(3,filmId);
        });

    }

    @Override
    public Set<Integer> findFilmLikes(Integer filmId) {
        String sql ="SELECT USER_ID FROM LIKES WHERE FILM_ID = ?";
        return new HashSet<Integer>(jdbcTemplate.query(sql, (rs,rowNum) -> rs.getInt("USER_ID"),filmId));
    }

}
