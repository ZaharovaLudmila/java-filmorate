package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.dao.FilmDbStorage;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DBFilmTest {

    private final FilmDbStorage filmStorage;

    private Mpa mpa;
    private Set<Genre> genre;
    private Film film;


    @BeforeEach
    public void createFilm() {
        mpa = new Mpa(2, "PG");
        genre = new HashSet<>();
        genre.add(new Genre(1, "Комедия"));
        film = new Film();
        film.setName("Назад в будущее");
        film.setDescription("Жанр: фантастика, комедия, приключения");
        film.setReleaseDate(LocalDate.of(1985, Month.JULY, 3));
        film.setDuration(116);
        film.setMpa(mpa);
        film.setGenres(genre);

    }

    @Test
    public void testCreateFilm() {

        Optional<Film> filmOptional = filmStorage.create(film);

        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("id", 1)
                );
    }

    @Test
    public void testUpdateFilm() {

        filmStorage.create(film);
        film.setId(1);
        film.setDescription("Приключенческий фантастический фильм");
        filmStorage.update(film);
        Optional<Film> filmOptional = filmStorage.getFilmByID(1);
        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("description",
                                "Приключенческий фантастический фильм")
                );
    }

    @Test
    public void testDeleteFilm() {

        filmStorage.create(film);
        film.setId(1);
        filmStorage.delete(film);
        Optional<Film> filmOptional = filmStorage.getFilmByID(1);
        assertThat(filmOptional).isEmpty();
    }

    @Test
    public void testGetFilmByID() {
        filmStorage.create(film);
        film.setId(1);
        Optional<Film> filmOptional = filmStorage.getFilmByID(1);

        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("id", 1)
                );
    }

    @Test
    public void testFindAllFilm() {
        filmStorage.create(film);
        film.setId(1);
        List<Film> films = filmStorage.findAll();
        List<Film> films2 = new ArrayList<>();
        films2.add(film);
        assertTrue(films.equals(films2), "Список со всеми фильмами не равен ожидаемому");
    }

    @Test
    public void testGetPopularFilm() {
        filmStorage.create(film);
        film.setId(1);
        Film film2 = new Film();
        film2.setName("Назад в будущее");
        film2.setDescription("Жанр: фантастика, комедия, приключения");
        film2.setReleaseDate(LocalDate.of(1985, Month.JULY, 3));
        film2.setDuration(116);
        film2.setMpa(mpa);
        film2.setGenres(genre);
        filmStorage.create(film2);
        film2.setId(2);
        List<Film> popular = new ArrayList<>();
        popular.add(film);
        assertTrue(popular.equals(filmStorage.getPopularFilm(1)),
                "Список с популярными фальмами не равен ожидаемому");
    }
}
