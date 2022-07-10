package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.intrf.FilmStorage;

import java.util.*;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private Map<Integer, Film> films = new HashMap<>();
    private int filmId = 0;

    public List<Film> findAll() {
        return new ArrayList<>(films.values());
    }

    public Optional<Film> create(Film film) {
        film.setId(++filmId);
        films.put(film.getId(), film);
        return Optional.of(film);
    }

    public Optional<Film> update(Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
        } else {
            throw new FilmNotFoundException("Не удалось обновить фильм, фильм с id " + film.getId() + " не найден!");
        }
        return Optional.of(film);
    }

    public void delete(Film film) {
        films.remove(film.getId());
    }

    public Optional<Film> getFilmByID(int id) {
        if (films.containsKey(id)) {
            return Optional.of(films.get(id));
        } else {
            throw new FilmNotFoundException(
                    String.format("Фильм с id %d не найден!", id));
        }
    }

    @Override
    public List<Genre> getFilmGenres(int filmID) {
        return new ArrayList<>(getFilmByID(filmID).get().getGenres());
    }

    @Override
    public void addFilmsGenre(int filmId, int genreId) {
        //getFilmByID(filmId).get().getGenres().add(genreId);
    }

    @Override
    public void deleteFilmGenres(int filmID) {
        getFilmByID(filmId).get().getGenres().clear();
    }

    @Override
    public List<Film> getPopularFilm(int count) {
        /*List<Film> films = findAll();
        films.sort(Collections.reverseOrder(Comparator.comparing(film -> film.getLikes().size())));
        return films.stream().limit(count).collect(Collectors.toList());*/
        return new ArrayList<>();
    }

}
