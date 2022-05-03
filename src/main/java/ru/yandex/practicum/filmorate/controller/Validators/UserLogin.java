package ru.yandex.practicum.filmorate.controller.Validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = UserLoginVaidator.class)
public @interface UserLogin {
    String message() default "{Login не должен быть пустым или содержать пробелов.}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}