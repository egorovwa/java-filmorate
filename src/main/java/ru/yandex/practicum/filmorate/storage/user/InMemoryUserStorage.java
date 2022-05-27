package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserAlreadyExistsException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    int id = 1;
    Map<Integer, User> userMap = new HashMap<>();

    @Override
    public User findById(int findedId) {
        if (userMap.containsKey(findedId)) {
            return userMap.get(findedId);
        } else {
            log.debug("Ползователь не найден. id {}",String.valueOf(findedId));
            throw new UserNotFoundException("Ползователь не найден.","id",String.valueOf(findedId));
        }

    }

    @Override
    public User findByEmail(String email) {
        return userMap.values().stream().filter(u -> u.getEmail().equals(email))
                .findFirst().orElseThrow();       //NoSuchElementException
    }

    @Override
    public User add(User user) {
        if (user.getId() == null) {
            user.setId(id);
            userMap.put(user.getId(), user);
            id++;
            return user;
        } else if (!userMap.containsKey(user.getId())) {
            userMap.put(user.getId(), user);
            return user;
        } else {
            log.debug("Ползователь уже существует.Id {}", String.valueOf(user.getId()));
            throw new UserAlreadyExistsException("Ползователь уже существует.","Id", String.valueOf(user.getId()));
        }

    }

    @Override
    public User update(User user) {
        if (user.getId() != null && userMap.containsKey(user.getId())) {
            userMap.put(user.getId(), user);
        } else {
            throw new UserNotFoundException("Ползователь не найден.","id",String.valueOf(user.getId()));
// TODO: 27.05.2022 проверить
            /*            user.setId(id);
            id++;
            userMap.put(user.getId(), user);*/
        }
        return user;
    }

    @Override
    public User delete(User user) {
        if (userMap.containsKey(user.getId())) {
            userMap.remove(user.getId());
            return user;
        } else {
            throw new UserNotFoundException("Ползователь не найден.","id",String.valueOf(user.getId()));
        }
    }

    @Override
    public Collection<User> findAll() {
        return Collections.unmodifiableCollection(userMap.values());
    }
}
