package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private FilmService filmService;
    private UserService userService;

    @Autowired
    public FilmController(FilmService filmService, UserService userService) {
        this.filmService = filmService;
        this.userService = userService;
    }

    @GetMapping()
    public List<Film> findAll() {
        return filmService.findAll();
    }

    @GetMapping("/{filmId}")
    public Film findByID(@PathVariable int filmId) {
        return filmService.getFilmById(filmId);
    }

    @PostMapping()
    public Film create(@Valid @RequestBody Film film) {
        film = filmService.create(film);
        log.info("Был добавлен новый фильм {}", film.getName());
        return film;
    }

    @PutMapping()
    public Film update(@Valid @RequestBody Film film) {
        film = filmService.update(film);
        log.info("Фильм {} был обновлен", film.getName());
        return film;
    }

    @PutMapping("/{id}/like/{userId}")
    public boolean addLike(@PathVariable int id, @PathVariable long userId) {
        userService.getUserById(userId);
        filmService.addLike(id, userId);
        log.info("Фильму {} добавлен like  от пользователя {}", filmService.getFilmById(id).getName(),
                userService.getUserById(userId).getName());
        return true;
    }

    @DeleteMapping("/{id}/like/{userId}")
    public boolean deleteLike(@PathVariable int id, @PathVariable long userId) {
        userService.getUserById(userId);
        filmService.deleteLike(id, userId);
        log.info("Удален like к фильму {} от пользователя {}", filmService.getFilmById(id).getName(),
                userService.getUserById(userId).getName());
        return true;
    }

    @GetMapping("/popular")
    public List<Film> findPopularFilms(@RequestParam(defaultValue = "10") @PathVariable int count) {
        return filmService.getPopularFilm(count);
    }
}
