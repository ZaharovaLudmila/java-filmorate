package ru.yandex.practicum.filmorate.storage.intrf;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface LikesStorage {

    void addLike(int filmId, long userId);

    void deleteLike(int filmId, long userId);

}
