package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {
    User findById(int id);
    User findByEmail(String email);
    User add(User user);
    User update(User user);
    User delete(User user);
    Collection<User> findAll();
}
