package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {

    private Map<Long, User> users = new HashMap<>();
    private long userId;

    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    public User create(User user) {
        user.setId(++userId);
        users.put(user.getId(), user);
        return user;
    }

    public User update(User user) {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
        } else {
            throw new UserNotFoundException(
                    String.format("Не удалось обновить пользователя, пользователь с id %d не найден!", user.getId()));
        }
        return user;
    }

    public User getUserByID(long id) {
        if (users.containsKey(id)) {
            return users.get(id);
        } else {
            throw new UserNotFoundException(
                    String.format("Пользователь с id %d не найден!", id));
        }
    }
}
