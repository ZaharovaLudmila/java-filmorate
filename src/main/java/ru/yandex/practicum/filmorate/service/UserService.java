package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.dao.daoInterface.FriendsStorage;
import ru.yandex.practicum.filmorate.dao.daoInterface.UserStorage;

import java.util.List;

@Service
public class UserService {

    @Qualifier("UserDbStorage")
    UserStorage userStorage;
    FriendsStorage friendsStorage;

    @Autowired
    public UserService(UserStorage userStorage, FriendsStorage friendsStorage) {
        this.userStorage = userStorage;
        this.friendsStorage = friendsStorage;
    }

    public List<User> findAll() {
        return userStorage.findAll();
    }

    public User create(User user) {
        Validator.userValid(user);
        return userStorage.create(user).get();
    }

    public User update(User user) {
        Validator.userValid(user);
        getUserById(user.getId());
        return userStorage.update(user).get();
    }

    public void addFriend(long userId, long friendId) {
        getUserById(userId);
        getUserById(friendId);
        friendsStorage.addFriend(userId, friendId);
    }

    public void deleteFriend(long userId, long friendId) {
        friendsStorage.deleteFriend(userId, friendId);
    }

    public List<User> commonFriends(long userId, long friendId) {
        return userStorage.commonFriends(userId, friendId);
    }

    public User getUserById(long id) {

        return userStorage.getUserByID(id).orElseThrow(()-> new UserNotFoundException("Пользователь с " +
                id + " не найден."));
    }

    public List<User> getUserFriends(long userId) {
       return userStorage.getUserFriends(userId);
    }
}
