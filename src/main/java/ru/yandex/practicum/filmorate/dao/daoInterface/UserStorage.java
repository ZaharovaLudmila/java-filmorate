package ru.yandex.practicum.filmorate.dao.daoInterface;

import ru.yandex.practicum.filmorate.model.User;
import java.util.List;
import java.util.Optional;

public interface UserStorage {

    List<User> findAll();

    Optional<User> create(User user);

    Optional<User> update(User user);

    void delete(User user);

    Optional<User> getUserByID(long id);

    public List<User> commonFriends(long userId, long friendId);

    public List<User> getUserFriends(long userId);
}
