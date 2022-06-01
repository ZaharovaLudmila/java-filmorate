package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidatorFilmTest {

    FilmController filmController;
    Film film;

    @BeforeEach
    public void createFilmController() {
        filmController = new FilmController();
        //film = new Film("Назад в будущее");
        film = new Film();
        film.setName("Назад в будущее");
        film.setId(1);
        film.setDescription("Жанр: фантастика, комедия, приключения");
        film.setReleaseDate(LocalDate.of(1985, Month.JULY, 3));
        film.setDuration(116);
    }

    @Test
    public void filmValidTestName() {
        film.setName("Назад в будущее");
        assertEquals(film, filmController.create(film), "Фильм с корректным названием не добавлен");
    }

    @Test
    public void filmValidTestEmptyName() {
        film.setName("");
        assertThrows(ValidationException.class, () -> filmController.create(film),
                "Добавлен фильм с некорректным названием");
    }

    @Test
    public void filmValidTestDescription() {
        film.setDescription("Жанр: фантастика, комедия, приключения");
        assertEquals(film, filmController.create(film), "Фильм с корректной длиной описания не добавлен");
    }

    @Test
    public void filmValidTestInvalidDescription() {
        film.setDescription("Жанр: фантастика, комедия, приключения. Подросток Марти с помощью машины времени, " +
                "сооружённой его другом-профессором доком Брауном, попадает из 80-х в далекие 50-е. Там он " +
                "встречается со своими будущими родителями, ещё подростками, и другом-профессором, совсем молодым.");
        assertThrows(ValidationException.class, () -> filmController.create(film),
                "Добавлен фильм с описанием превышаюшим допустимое количество символов");
    }

    @Test
    public void filmValidTestEmptyDescription() {
        film.setDescription("");
        assertEquals(film, filmController.create(film), "Фильм с пустым описанием не добавлен");
    }

    @Test
    public void filmValidTestReleaseDate() {
        film.setReleaseDate(LocalDate.of(1985, Month.JULY, 3));
        assertEquals(film, filmController.create(film), "Фильм с корректной датой релиза не добавлен");
    }

    @Test
    public void filmValidTestInvalidReleaseDate() {
        film.setReleaseDate(LocalDate.of(1800, Month.JULY, 3));
        assertThrows(ValidationException.class, () -> filmController.create(film),
                "Добавлен фильм с некорректной датой релиза");
    }

    @Test
    public void filmValidTestNullReleaseDate() {
        film.setReleaseDate(null);
        assertEquals(film, filmController.create(film), "Фильм с пустой датой релиза не добавлен");
    }

    @Test
    public void filmValidTestDuration() {
        film.setDuration(116);
        assertEquals(film, filmController.create(film), "Фильм с корректной продолжительностью не добавлен");
    }

    @Test
    public void filmValidTestInvalidDuration() {
        film.setDuration(-5);
        assertThrows(ValidationException.class, () -> filmController.create(film),
                "Добавлен фильм с некорректной продолжительностью");
    }
}
