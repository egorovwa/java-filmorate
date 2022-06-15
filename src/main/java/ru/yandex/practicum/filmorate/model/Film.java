package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import ru.yandex.practicum.filmorate.controller.Validators.FilmReleaseDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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
    @NonNull
    Integer mpaId;

    Set<Integer> genresId = new HashSet<>(); // TODO: 14.06.2022 продумать
    Set<Integer> likeSet = new HashSet<>();
}
