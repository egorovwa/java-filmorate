package ru.yandex.practicum.filmorate.controller.Validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserLoginVaidator implements ConstraintValidator<UserLogin,String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !((value.isBlank()) || (value.contains(" ")));
    }
}
