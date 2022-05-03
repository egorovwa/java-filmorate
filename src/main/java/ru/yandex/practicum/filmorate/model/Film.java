package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.yandex.practicum.filmorate.controller.Validators.FilmReliaseDate;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class Film {
    @EqualsAndHashCode.Include
    Integer id;
    @NotBlank(message = "Название фильма не должно быть пустым.")
    String name;
    @Size(max = 200, message = "Размер описания привышает 200 символов.")
    String description;
    @FilmReliaseDate
    LocalDate releaseDate;
    @Positive
    Integer duration;
}
