package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping()
    public List<User> findAll() {
        return userService.findAll();
    }

    @PostMapping()
    public User create(@Valid @RequestBody User user) {
        user = userService.create(user);
        log.info("Был добавлен новый пользователь {}, id {}, email {}", user.getName(), user.getId(), user.getEmail());
        return user;
    }

    @PutMapping()
    public User update(@Valid @RequestBody User user) {
        user = userService.update(user);
        log.info("пользователь {}, id {}, email {} был обновлен", user.getName(), user.getId());
        return user;
    }

    @GetMapping("/{userId}")
    public User findByID(@PathVariable long userId) {
        return userService.getUserById(userId);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public boolean addFriend(@PathVariable long id, @PathVariable long friendId) {
        userService.addFriend(id, friendId);
        log.info("Пользователь {} успешно добавлен в друзья пользователя {}",
                userService.getUserById(friendId).getName(),
                userService.getUserById(id).getName());
        return true;
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public boolean deleteFriend(@PathVariable long id, @PathVariable long friendId) {
        userService.deleteFriend(id, friendId);
        log.info("Пользователь {} удален из друзей пользователя {}", userService.getUserById(friendId).getName(),
                userService.getUserById(id).getName());
        return true;
    }

    @GetMapping("/{id}/friends")
    public List<User> findUserFriends(@PathVariable long id) {
        return userService.getUserFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> findCommonFriends(@PathVariable long id, @PathVariable long otherId) {
        return userService.commonFriends(id, otherId);
    }
}
