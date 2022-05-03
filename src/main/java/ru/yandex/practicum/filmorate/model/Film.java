package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.yandex.practicum.filmorate.controller.Validators.FilmReleaseDate;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class Film {
    @EqualsAndHashCode.Include
    Integer id;
    @NotBlank(message = "Название фильма не должно быть пустым.")
    String name;
    @Size(min = 1,max = 200,message = "Размер описания долженбыть болше 1 и меньше 200 символов.")
    String description;
    @FilmReleaseDate
    LocalDate releaseDate;
    @Positive
    Integer duration;
}
