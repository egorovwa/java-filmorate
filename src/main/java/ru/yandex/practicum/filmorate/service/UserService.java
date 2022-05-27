package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriend(int userId, int friendId) {
        userStorage.findById(userId).getFriends().add(friendId);
        userStorage.findById(friendId).getFriends().add(userId);
        log.debug("Пользователь {} добавил в друзья {}",userId,friendId);
    }
    public void deleteFriend(int userId, int friendId){
        userStorage.findById(userId).getFriends().remove(friendId);
        userStorage.findById(friendId).getFriends().remove(userId);
        log.debug("Пользователь {} удалил из друзей {}",userId,friendId);
    }

    public Collection<User> findCommonFriends(int id, int other) {
        Set<Integer> userFriends = userStorage.findById(id).getFriends();
        Set<Integer> otherFriends = userStorage.findById(other).getFriends();
        List<Integer> commonId = userFriends.stream().filter(otherFriends::contains)
                .collect(Collectors.toList());
        return commonId.stream().map(r -> {
            try {
                log.debug("Передан список пересекающихся друзей пользователя {} с {}", id,other);
                return userStorage.findById(r);
            } catch (UserNotFoundException e) {
                userFriends.remove(r);
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public Collection<User> getAllFriends(int id) {
        Set<Integer> friendsId = userStorage.findById(id).getFriends();

        return friendsId.stream().map(r -> {
            try {
                return userStorage.findById(r);
            } catch (UserNotFoundException e) {
                friendsId.remove(r);
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public User addUser(User user) {
       checkUserName(user);
        return userStorage.add(user);
    }

    public User update(User user) {
        checkUserName(user);
        return userStorage.update(user);
    }

    public Collection<User> findAll() {
        return userStorage.findAll();
    }
    public User findUserById(int id) {
       return userStorage.findById(id);
    }

    private void checkUserName(User user) {
        if (!StringUtils.hasText(user.getName())) {
            user.setName(user.getLogin());
        }
    }
}
