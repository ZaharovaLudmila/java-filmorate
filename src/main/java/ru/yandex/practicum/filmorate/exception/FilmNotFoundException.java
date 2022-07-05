package ru.yandex.practicum.filmorate.exception;

public class FilmNotFoundException extends IllegalStateException{

    public FilmNotFoundException(String message) {
        super(message);
    }
}
