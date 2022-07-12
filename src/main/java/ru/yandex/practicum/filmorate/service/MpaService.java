package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.GenreNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.dao.daoInterface.MPAStorage;

import java.util.List;

@Service
public class MpaService {

    MPAStorage mpaStorage;

    @Autowired
    public MpaService(MPAStorage mpaStorage) {
        this.mpaStorage = mpaStorage;
    }

    public List<Mpa> findAll() {
        return mpaStorage.findAll();
    }

    public Mpa getMPAByID(int id) {
        return mpaStorage.getMPAByID(id).orElseThrow(() -> new GenreNotFoundException("Рейтинг MPA с " + id +
                " не найден."));
    }
}

