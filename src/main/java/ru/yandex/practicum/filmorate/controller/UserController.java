package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.Validator;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private Map<Integer, User> users = new HashMap<>();
    private int userId = 0;

    @GetMapping()
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @PostMapping()
    public User create(@Valid @RequestBody User user) {
        Validator.userValid(user);
        user.setId(++userId);
        users.put(user.getId(), user);
        log.info("Был добавлен новый пользователь {}, id {}, email {}", user.getName(), user.getId(), user.getEmail());
        return user;
    }

    @PutMapping()
    public User update(@Valid @RequestBody User user) {
        Validator.userValid(user);
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            log.info("Был добавлен новый пользователь {}, id {}, email {}", user.getName(), user.getId());
        } else {
            log.info("Не удалось обновить пользователя, пользователь с id  {}, не найден!", user.getId());
            throw new ValidationException("Не удалось обновить пользователя, пользователь с id " + user.getId()
                    + " не найден!");
        }
        return user;
    }
}
