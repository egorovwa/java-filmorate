package ru.yandex.practicum.filmorate.exception;

public class NotFoundException extends InvalidRequestException{
    public NotFoundException(String message, String parm, String value) {
        super(message, parm, value);
    }
}
