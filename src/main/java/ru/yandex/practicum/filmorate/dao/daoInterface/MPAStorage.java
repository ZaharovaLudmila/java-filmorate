package ru.yandex.practicum.filmorate.dao.daoInterface;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;
import java.util.Optional;

public interface MPAStorage {

    List<Mpa> findAll();

    Optional<Mpa> getMPAByID(int id);
}
