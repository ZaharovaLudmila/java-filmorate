package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.GenreNotFoundException;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.intrf.MPAStorage;

import java.util.List;

@Service
public class MPAService {

    MPAStorage mpaStorage;

    @Autowired
    public MPAService(MPAStorage mpaStorage) {
        this.mpaStorage = mpaStorage;
    }

    public List<MPA> findAll() {
        return mpaStorage.findAll();
    }

    public MPA getMPAByID(int id) {
        return mpaStorage.getMPAByID(id).orElseThrow(() -> new GenreNotFoundException("Рейтинг MPA с " + id +
                " не найден."));
    }
}

