package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;


import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class UserController {
    int id = 0;
    Map<Integer, User> userMap;

    public UserController() {
        userMap = new HashMap<>();
    }

    @PostMapping("/users")
    public User addUser(@Valid @RequestBody User user) {
        user.setId(id);
        id++;
        userMap.put(user.getId(), user);
        log.info("Добавлен новый ползователь: " + user.getLogin());
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
            log.info("Обновлен ползователь: " + user.getLogin());
            return user;
        } else {
            checkUserName(user);
            user.setId(id);
            id++;
            userMap.put(user.getId(), user);
            log.info("Добавлен новый ползователь: " + user.getLogin());
            return user;
        }
    }

    @GetMapping("/users")
    public List<User> findAll() {
        log.trace("Передан список всех Users");
        return new ArrayList<User>(userMap.values());
    }

    private void checkUserName(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
