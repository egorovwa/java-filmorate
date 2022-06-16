package ru.yandex.practicum.filmorate.exception;

public class GenreNotFoundException extends NotFoundException {
    public GenreNotFoundException(String message, String parm, String value) {
        super(message, parm, value);
    }
}
