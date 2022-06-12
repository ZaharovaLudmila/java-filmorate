package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.time.Month;

@Slf4j
public class Validator {

    public static boolean filmValid(Film film) {
        LocalDate minDate = LocalDate.of(1895, Month.DECEMBER, 28);
        if (film.getName() == null || film.getName().isBlank()) {
            log.info("Ошибка при добавлении фильма! Название фильма не может быть пустым!");
            throw new ValidationException("Невозможно добавить фильм, название фильма не может быть пустым!");
        }

        if (film.getDescription() != null && film.getDescription().length() >200) {
            log.info("Ошибка при добавлении фильма! Превышена максимальная длина описания 200!");
            throw new ValidationException("Невозможно добавить фильм! Превышена максимальная длина описания 200");
        }

        if (film.getReleaseDate() != null && film.getReleaseDate().isBefore(minDate)) {
            log.info("Ошибка при добавлении фильма {}. Невалидная дата релиза {}",
                    film.getName(), film.getReleaseDate());
            throw new ValidationException("Невозможно добавить фильм, дата релиза которого меньше " + minDate);
        }

        if(film.getDuration() < 0) {
            log.info("Ошибка при добавлении фильма {}. Продолжительность фильма должна быть положительной!",
                    film.getName(), film.getReleaseDate());
            throw new ValidationException("Продолжительность фильма должна быть положительной!");
        }
        return true;
    }

    public static boolean userValid(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            log.info("Некорректный адрес электронной почты {} пользователя {} (id {})", user.getEmail(), user.getName(),
                    user.getId());
            throw new ValidationException("Некорректный адрес электронной почты " + user.getEmail());
        }

        if (user.getLogin() == null || user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            log.info("Некорректный логин {} пользователя {} (id {})", user.getLogin(), user.getName(), user.getId());
            throw new ValidationException("Логин должен быть заполнен и не должен содержать пробелы!");
        }

        if (user.getBirthday() != null && user.getBirthday().isAfter(LocalDate.now())) {
            log.info("Некорректная дата рождения {} пользователя {} (id {})", user.getBirthday(),
                    user.getName(), user.getId());
            throw new ValidationException("Указана некорректная дата рождения!");
        }

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.info("Имя пользователя c id {} не было заполнено! Присвоено имя: {}", user.getId(), user.getName());
        }
        return true;
    }
}
