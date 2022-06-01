package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.service.Validator;

import javax.validation.Valid;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private Map<Integer, Film> films = new HashMap<>();
    private int filmId = 0;

    @GetMapping()
    public List<Film> findAll() {
        return new ArrayList<>(films.values());
    }

    @PostMapping()
    public Film create(@Valid @RequestBody Film film) {
        if (Validator.filmValid(film)) {
            film.setId(++filmId);
            films.put(film.getId(), film);
            log.info("Был добавлен новый фильм {}", film.getName());
        }
        return film;
    }

    @PutMapping()
    public Film update(@Valid @RequestBody Film film) {
        if (Validator.filmValid(film) && films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.info("Фильм {} был обновлен", film.getName());
        } else {
            log.info("Не удалось обновить фильм, фильм с id {}, не найден!", film.getId());
            throw new ValidationException("Не удалось обновить фильм, фильм с id " + film.getId() + " не найден!");
        }
        return film;
    }
}
