package ru.yandex.practicum.filmorate.storage.user.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.user.FriendshipDao;

import java.util.*;

@Component
public class FriendshipDaoImpl implements FriendshipDao {
    JdbcTemplate jdbcTemplate;

    public FriendshipDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Set<Integer> findFriendshipsByUserId(Integer userId) {
        String sql = "SELECT FRIEND_ID FROM FRIENDSHIP WHERE USER_ID=?";
        Set<Integer> friends = new HashSet<>(jdbcTemplate.query(sql,
                (rs, rowNum) -> rs.getInt("FRIEND_ID"), userId));
        return friends;
    }

    @Override
    public void addFriend(Integer userId, Integer friendId) {
        String insertSql = "INSERT INTO FRIENDSHIP(user_id, friend_id, status) VALUES ( ?,?,? ) ON CONFLICT DO NOTHING";
        Optional<Integer> mayBeFriendshipId = checkFrindship(userId, friendId);
        if (mayBeFriendshipId.isPresent()) {
            jdbcTemplate.update(insertSql, ps -> {
                ps.setInt(1, userId);
                ps.setInt(2, friendId);
                ps.setBoolean(3, true);
            });
            String udateStatusSql = "UPDATE FRIENDSHIP SET STATUS=? WHERE ID=?";
            jdbcTemplate.update(udateStatusSql, ps -> {
                ps.setBoolean(1, true);
                ps.setInt(2, mayBeFriendshipId.get());
            });
        } else {
            jdbcTemplate.update(insertSql, ps -> {
                ps.setInt(1, userId);
                ps.setInt(2, friendId);
                ps.setBoolean(3, false);
            });
        }
    }

    @Override
    public Optional<Integer> checkFrindship(Integer userId, Integer friendId) {
        String sql = "SELECT ID FROM FRIENDSHIP WHERE USER_ID=? AND FRIEND_ID=?";
        Optional<Integer> mayBeFriendshipId;
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, friendId, userId);
        if (rowSet.next()) {
            return Optional.of(rowSet.getInt("ID"));
        } else return Optional.empty();
    }
}
