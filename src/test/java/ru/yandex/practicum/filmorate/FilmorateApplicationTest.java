package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.GenreService;
import ru.yandex.practicum.filmorate.service.MpaService;
import ru.yandex.practicum.filmorate.service.UserService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmoRateApplicationTests {
    private final UserService userService;
    private final FilmService filmService;
    private final MpaService mpaService;
    private final GenreService genreService;

    //UserService tests

    @Test
    void testFindUserByIdWhenPrisent() throws UserNotFoundException {

        User user = userService.findUserById(1);

        assertEquals(user.getName(), "name");
        assertEquals(user.getEmail(), "email@mail.ru");
        assertEquals(user.getBirthday(), LocalDate.of(1999, 9, 9));
    }

    @Test
    void testFindNotFoundUser() {

        assertThrows(UserNotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                User user = userService.findUserById(99);
            }
        });
    }

    @Test
    void testFindAllUsers() {
        Collection<User> userCollection = userService.findAll();
        assertEquals(2, userCollection.size());
        assertTrue(userCollection.stream().anyMatch(r -> r.getName().equals("name")));
    }

    @Test
    @DirtiesContext
    void testAddUser() throws UserAlreadyExistsException {
        User user = new User(null, "added@mail.ru",
                "addedUser", null, LocalDate.of(1874, 1, 1), null);
        User addedUser = userService.addUser(user);

        assertEquals(3, addedUser.getId());
        assertTrue(userService.findAll().stream().anyMatch(r -> r.getEmail().equals("added@mail.ru")));
        assertEquals(3, userService.findAll().size());
        assertEquals(user.getLogin(), addedUser.getName());
    }

    @Test
    @DirtiesContext
    void testUpdateUserNormal() throws UserNotFoundException {
        User user = new User(1, "updated@mail.ru",
                "updatedUser", "updateName", LocalDate.of(1974, 1, 1), null);
        userService.update(user);
        assertEquals("updateName", userService.findUserById(1).getName());
        assertEquals(2, userService.findAll().size());
    }

    @Test
    @DirtiesContext
    void testUpdateNotFoundUser() {
        assertThrows(UserNotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                User user = new User(47, "updated@mail.ru",
                        "updatedUser", "updateName", LocalDate.of(1974, 1, 1), null);
                userService.update(user);
            }
        });
    }

    @Test
    @DirtiesContext
    void testFrienship() throws UserNotFoundException, UserAlreadyExistsException {
        assertEquals(0, userService.getAllFriends(1).size());

        userService.addFriend(1, 2);
        assertTrue(userService.getAllFriends(1).stream().anyMatch(r -> r.getId() == 2));
        assertEquals(0, userService.getAllFriends(2).size());

        User user = new User(null, "added@mail.ru",
                "addedUser", null, LocalDate.of(1874, 1, 1), null);
        User addedUser = userService.addUser(user);
        userService.addFriend(3, 2);
        assertEquals(true, userService.findCommonFriends(1, 3).stream()
                .findAny().map(User::getId).isPresent());
        userService.deleteFriend(1, 2);
        assertTrue(userService.findUserById(1).getFriends().isEmpty());
    }


    // FilmService Tests
    @Test
    void testFilnGetFilms() throws FilmNotFoundException, SQLException, MpaNotFoundException {
        assertEquals(2, filmService.findAll().size());
        Film film1 = filmService.findById(1);
        assertEquals("Film1", film1.getName());
        Mpa mpa = mpaService.getById(1);
        assertEquals(mpa, film1.getMpa());
        assertThrows(FilmNotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                filmService.findById(78);
            }
        });
    }

    @Test
    @DirtiesContext
    void testsLikes() throws FilmNotFoundException, SQLException, LikeAlreadyExistsException, MpaNotFoundException {
        filmService.addLike(1,1);
        assertTrue(filmService.findById(1).getLikeSet().stream().anyMatch(r->r==1));
        assertEquals(1, filmService.findById(1).getRate());
        System.out.println(filmService.findPopularFilm(2));
        assertTrue(filmService.findPopularFilm(2).stream().findFirst().get().getId()==1);
    }

}