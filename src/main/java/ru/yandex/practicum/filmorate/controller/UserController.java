package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Collection;


@RestController
@Slf4j
@Validated
public class UserController {
    UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;

    }

    @PostMapping("/users")
    public User addUser(@Valid @RequestBody User user) {
        log.info("Добавлен новый ползователь: {}", user.getLogin());
        return userService.addUser(user);
    }



    @PutMapping("/users")
    public User update(@Valid @RequestBody User user) {
        return userService.update(user);
    }

    @GetMapping("/users")
    public Collection<User> findAll() {
        log.trace("Передан список всех Users");
        return userService.findAll();
    }

    @GetMapping("/users/{id}")
    public User findUserById(@PathVariable @Min(0) int id) {
        return userService.findUserById(id);
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public void addFriend(@PathVariable @Min(0) int id, @PathVariable @Min(0) int friendId) {

        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable @Min(0) int id, @PathVariable @Min(0) int friendId) {
        userService.deleteFriend(id, friendId);
    }

    @GetMapping("/users/{id}/friends")
    public Collection<User> findFriends(@PathVariable @Min(0) int id) {
        return userService.getAllFriends(id);
    }
    @GetMapping("/users/{id}/friends/common/{otherId}")
    public Collection<User> findCommonFriends(@PathVariable @Min(0) int id,@PathVariable @Min(0) int otherId){
        return  userService.findCommonFriends(id,otherId);
    }

}
