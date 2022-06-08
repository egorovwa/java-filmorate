package ru.yandex.practicum.filmorate.exception;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(String message, String parm, String value) {
        super(message, parm, value);
    }
}
