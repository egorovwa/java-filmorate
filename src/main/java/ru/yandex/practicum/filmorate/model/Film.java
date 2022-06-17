package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import ru.yandex.practicum.filmorate.controller.Validators.FilmReleaseDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
public class Film {
    @EqualsAndHashCode.Include
    Integer id;
    @NotBlank(message = "Название фильма не должно быть пустым.")
    String name;
    @Size(min = 1, max = 200, message = "Размер описания долженбыть болше 1 и меньше 200 символов.")
    String description;
    @FilmReleaseDate
    LocalDate releaseDate;
    @Positive
    Integer duration;
    int rate = 0;
    Set<Integer> likeSet;
    Mpa mpa;
    Set<Genre> genres;

    public Film() {
    }

    public Film(Integer id, String name, String description, LocalDate releaseDate, Integer duration, Integer rate,
                Set<Integer> likeSet, Mpa mpa, Set<Genre> genres) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.rate = rate;
        this.likeSet = likeSet;
        this.mpa = mpa;
        this.genres = genres;
    }

    public Film(String name, String description, LocalDate releaseDate, Integer duration, Integer rate, Mpa mpa) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.rate = rate;
        this.mpa = mpa;
    }

}
