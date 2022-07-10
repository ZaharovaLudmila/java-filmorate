package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.intrf.UserStorage;

import java.util.*;

@Component
public class InMemoryUserStorage implements UserStorage {

    private Map<Long, User> users = new HashMap<>();
    private long userId;

    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    public Optional<User> create(User user) {
        user.setId(++userId);
        users.put(user.getId(), user);
        return Optional.of(user);
    }

    public Optional<User> update(User user) {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
        } else {
            throw new UserNotFoundException(
                    String.format("Не удалось обновить пользователя, пользователь с id %d не найден!", user.getId()));
        }
        return Optional.of(user);
    }

    @Override
    public void delete(User user) {
        users.remove(user.getId());
    }

    public Optional<User> getUserByID(long id) {
        if (users.containsKey(id)) {
            return Optional.of(users.get(id));
        } else {
            throw new UserNotFoundException(
                    String.format("Пользователь с id %d не найден!", id));
        }
    }

    @Override
    public List<User> commonFriends(long userId, long friendId) {
        /*User user = userStorage.getUserByID(userId);
        User friend = userStorage.getUserByID(friendId);
        ArrayList<Long> userFriends = new ArrayList<>();//(user.getFriends());
        ArrayList<Long> friendFriends = new ArrayList<>();//(friend.getFriends());
        userFriends.retainAll(friendFriends);
        return userFriends.stream().map(id -> userStorage.getUserByID(id)).collect(Collectors.toList());*/
        return new ArrayList<>();
    }

    @Override
    public List<User> getUserFriends(long userId) {
       /* List<Long> friendsList = new ArrayList<>();//(userStorage.getUserByID(userId).getFriends());
        return friendsList.stream().map(id -> userStorage.getUserByID(id)).collect(Collectors.toList());*/
        return new ArrayList<>();
    }
}
