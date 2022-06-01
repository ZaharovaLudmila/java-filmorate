package ru.yandex.practicum.filmorate.exception;

public class ValidationException extends IllegalStateException {

    public ValidationException(String message) {
        super(message);
    }
}
