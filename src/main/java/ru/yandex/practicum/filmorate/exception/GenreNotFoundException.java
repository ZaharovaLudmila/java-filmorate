package ru.yandex.practicum.filmorate.exception;

public class GenreNotFoundException extends IllegalStateException {

    public GenreNotFoundException(String message) {
        super(message);
    }
}
