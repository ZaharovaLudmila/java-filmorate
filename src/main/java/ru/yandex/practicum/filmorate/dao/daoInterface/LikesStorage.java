package ru.yandex.practicum.filmorate.dao.daoInterface;

public interface LikesStorage {

    void addLike(int filmId, long userId);

    void deleteLike(int filmId, long userId);

}
