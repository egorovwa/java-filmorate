package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@RestController
@Slf4j
public class UserController {
    int id = 0;
    final Map<Integer, User> userMap;

    public UserController() {
        userMap = new ConcurrentHashMap<>();
    }

    @PostMapping("/users")
    public User addUser(@Valid @RequestBody User user) {
        user.setId(id);
        id++;
        userMap.put(user.getId(), user);
        log.info("Добавлен новый ползователь: {} всего пользавателей: {}", user.getLogin(),userMap.size());
        return user;
    }
@ExceptionHandler(ValidationException.class)
@ResponseStatus(HttpStatus.BAD_REQUEST)
    private Response checkUserEmail(ValidationException e) {
        return new Response();

    }

    @PutMapping("/users")
    public User update(@Valid @RequestBody User user) {
        if (user.getId() != null && userMap.containsKey(user.getId())) {
            userMap.put(user.getId(), user);
            log.info("Обновлен ползователь: {}", user.getLogin());
        } else {
            checkUserName(user);
            user.setId(id);
            id++;
            userMap.put(user.getId(), user);
            log.info("Добавлен новый ползаватель: {} всего пользавателей: {}", user.getLogin(),userMap.size());
        }
        return user;
    }

    @GetMapping("/users")
    public Collection<User> findAll() {
        log.trace("Передан список всех Users");
        return Collections.unmodifiableCollection(userMap.values());
    }

    private void checkUserName(User user) {
        if (StringUtils.hasText(user.getName())) {
            user.setName(user.getLogin());
        }
    }
}
