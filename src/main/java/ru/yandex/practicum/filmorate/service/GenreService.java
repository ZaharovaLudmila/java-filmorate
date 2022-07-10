package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.GenreNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.intrf.GenreStorage;

import java.util.List;

@Service
public class GenreService {

    GenreStorage genreStorage;

    @Autowired
    public GenreService(GenreStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    public List<Genre> findAll() {
        return genreStorage.findAll();
    }

    public Genre getGenreByID(int id) {
        return genreStorage.getGenreByID(id).orElseThrow(() -> new GenreNotFoundException("Жанр с " + id + " не найден."));
    }
}
