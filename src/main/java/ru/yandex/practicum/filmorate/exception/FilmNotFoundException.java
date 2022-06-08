package ru.yandex.practicum.filmorate.exception;

public class FilmNotFoundException extends NotFoundException {

    public FilmNotFoundException(String message, String parm, String value) {
        super(message, parm, value);
    }
}
