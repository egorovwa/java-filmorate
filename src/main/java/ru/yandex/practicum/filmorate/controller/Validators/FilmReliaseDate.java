package ru.yandex.practicum.filmorate.controller.Validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = FilmReliaseDateVaidator.class)
public @interface FilmReliaseDate {
    String message() default "{Дата релиза раньше 28,12,1895г.}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
