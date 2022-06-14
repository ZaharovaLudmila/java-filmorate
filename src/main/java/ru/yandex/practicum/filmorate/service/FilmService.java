package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {

    FilmStorage filmStorage;

    @Autowired
    public FilmService(InMemoryFilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public List<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film create(Film film) {
        Validator.filmValid(film);
        return  filmStorage.create(film);
    }

    public Film update(Film film) {
        Validator.filmValid(film);
        return filmStorage.update(film);
    }

    public void addLike(int filmId, User user) {
        Film film = filmStorage.getFilmByID(filmId);
        film.addLike(user);
    }

    public void deleteLike(int filmId, User user) {
        Film film = filmStorage.getFilmByID(filmId);
        film.deleteLike(user);
    }

    public List<Film> getPopularFilm(int count) {
        List<Film> films = findAll();
        films.sort(Collections.reverseOrder(Comparator.comparing(film -> film.getLikes().size())));
        return films.stream().limit(count).collect(Collectors.toList());
    }

    public Film getFilmById(int id) {
        return filmStorage.getFilmByID(id);
    }
}
