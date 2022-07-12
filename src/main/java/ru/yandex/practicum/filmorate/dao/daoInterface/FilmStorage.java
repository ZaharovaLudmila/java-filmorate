package ru.yandex.practicum.filmorate.dao.daoInterface;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {

    Optional<Film> create(Film film);

    Optional<Film> update(Film film);

    List<Film> findAll();

    void delete(Film film);

    Optional<Film> getFilmByID(int id);

    List<Film> getPopularFilm(int count);

    List<Genre> getFilmGenres(int filmID);

    void addFilmsGenre(int filmId, int genreId);

    void deleteFilmGenres(int filmID);
}
