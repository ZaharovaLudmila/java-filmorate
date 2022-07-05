package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidatorUserTest {

    UserController userController;
    User user;

    @BeforeEach
    public void createUserController() {
        userController = new UserController(new UserService(new InMemoryUserStorage()));
        user = new User();
        user.setId(1);
        user.setEmail("test@user.ru");
        user.setLogin("Login");
        user.setName("Name");
        user.setBirthday(LocalDate.of(2000, Month.DECEMBER, 15));
    }

    @Test
    public void userValidTestEmail() {
        user.setEmail("test@user.ru");
        assertEquals(user, userController.create(user), "Пользователь с корректным email не добавлен");
    }

    @Test
    public void userValidTestInvalidEmail() {
        user.setEmail("это-неправильный?эмейл");
        assertThrows(ValidationException.class, () -> userController.create(user),
                "Добавлен пользователь с некорректным email");
        user.setEmail("test@user.ru");
    }

    @Test
    public void userValidTestEmptyEmail() {
        user.setEmail("");
        assertThrows(ValidationException.class, () -> userController.create(user),
                "Добавлен пользователь с некорректным email");
    }

    @Test
    public void userValidTestLogin() {
        user.setLogin("ThisCorrectLogin");
        assertEquals(user, userController.create(user), "Пользователь с корректным логином не добавлен");
    }

    @Test
    public void userValidTestInvalidLogin() {
        user.setLogin("Это некорретный логин");
        assertThrows(ValidationException.class, () -> userController.create(user),
                "Добавлен пользователь с некорректным логином");
    }

    @Test
    public void userValidTestEmptyLogin() {
        user.setLogin("");
        assertThrows(ValidationException.class, () -> userController.create(user),
                "Добавлен пользователь с некорректным логином");
    }

    @Test
    public void userValidTestName() {
        user.setName("Name User");
        assertEquals(user, userController.create(user), "Пользователь с корректным именем не добавлен");
    }

    @Test
    public void userValidTestEmptyName() {
        user.setName("");
        user.setLogin("LoginName");
        assertEquals(user.getLogin(), userController.create(user).getName(),
                "Имя пользователя не равно логину");
    }

    @Test
    public void userValidTestBirthday() {
        user.setBirthday(LocalDate.of(2000, Month.DECEMBER, 15));
        assertEquals(user, userController.create(user), "Пользователь с корректным днем рождения не добавлен");
    }

    @Test
    public void userValidTestInvalidBirthday() {
        user.setBirthday(LocalDate.of(2033, Month.DECEMBER, 15));
        assertThrows(ValidationException.class, () -> userController.create(user),
                "Добавлен пользователь с некорректной датой рождения");
    }
}
