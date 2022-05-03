package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.yandex.practicum.filmorate.controller.Validators.UserLogin;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
public class User {
    Integer id;
    @Email(message = "Не коректный Email.")
    final String email;

    @UserLogin
    final String login;
    String name;
    @Past(message = "Дата рождения не может быть в будущим.")
    final LocalDate birthday;
}
