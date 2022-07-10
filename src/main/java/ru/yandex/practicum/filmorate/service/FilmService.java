package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.LikesDao;
import ru.yandex.practicum.filmorate.storage.intrf.FilmStorage;
import ru.yandex.practicum.filmorate.storage.intrf.LikesStorage;

import java.util.ArrayList;
import java.util.List;

@Service
public class FilmService {

    @Qualifier("FilmDbStorage")
    FilmStorage filmStorage;
    LikesStorage likesStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, LikesStorage likesStorage) {
        this.filmStorage = filmStorage;
        this.likesStorage = likesStorage;
    }

    public List<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film create(Film film) {
        Validator.filmValid(film);
        return filmStorage.create(film).get();
    }

    public Film update(Film film) {
        Validator.filmValid(film);
        getFilmById(film.getId());
        return filmStorage.update(film).get();
    }

    public void addLike(int filmId, long userId) {
        getFilmById(filmId);
        likesStorage.addLike(filmId, userId);
    }

    public void deleteLike(int filmId, long userId) {
        getFilmById(filmId);
        likesStorage.deleteLike(filmId, userId);
    }

    public List<Film> getPopularFilm(int count) {
        return filmStorage.getPopularFilm(count);
    }

    public Film getFilmById(int id) {
        return filmStorage.getFilmByID(id).orElseThrow(() -> new UserNotFoundException("Фильм с " +
                id + " не найден."));
    }
}
