package ru.yandex.practicum.filmorate.storage.user.impl;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.JDBCException;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserAlreadyExistsException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
@Primary
@Slf4j
public class UserDbStorage implements UserStorage {
    JdbcTemplate jdbcTemplate;


    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static void setValues(PreparedStatement ps) {

    }

    @Override
    public User findById(int id) throws UserNotFoundException {
        String sql = "SELECT * FROM USERS WHERE USER_ID=?";
        Optional<User> user = jdbcTemplate.query(sql,(rs, colNum)->createUser(rs),String.valueOf(id))
                .stream().findAny();
        if (user.isPresent()){
            return user.get();
        } else {
            throw  new UserNotFoundException("Ползователь не найден.", "id", String.valueOf(id));
        }


    }

    private User createUser(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("USER_ID");
        String email = rs.getString("EMAIL");
        String login = rs.getString("LOGIN");
        String name = rs.getNString("NAME");
        LocalDate birthday = rs.getDate("BIRTHDAY").toLocalDate();
        Set<Integer> friends = getUserFriendsId(id);
        return new User(id, email, login, name, birthday, friends);
    }

    private Set<Integer> getUserFriendsId(Integer id) { // TODO: 15.06.2022 не забыть
        String sql = "";
        return null;

    }

    @Override
    public User findByEmail(String email) {
        return null;
    }

    @Override
    public User add(User user) throws UserAlreadyExistsException {
        String sql = "INSERT INTO USERS(email, login, name, birthday) VALUES (?,?,?,?)";
        int update = jdbcTemplate.update(sql, ps -> {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getName());
            ps.setDate(4,Date.valueOf(user.getBirthday()));
        });
        return user;
    }


    @Override
    public User update(User user) throws UserNotFoundException {
        return null;
    }

    @Override
    public User delete(User user) throws UserNotFoundException {
        return null;
    }

    @Override
    public Collection<User> findAll() {
        String sql = "SELECT * FROM USERS";

        return jdbcTemplate.query(sql,(rs,rowNum)->createUser(rs));
    }
}
