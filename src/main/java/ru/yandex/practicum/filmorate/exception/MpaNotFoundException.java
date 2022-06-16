package ru.yandex.practicum.filmorate.exception;

public class MpaNotFoundException extends NotFoundException{
    public MpaNotFoundException(String message, String parm, String value) {
        super(message, parm, value);
    }
}
