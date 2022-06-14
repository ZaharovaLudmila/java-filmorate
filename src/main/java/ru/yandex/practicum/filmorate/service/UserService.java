package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    UserStorage userStorage;

    @Autowired
    public UserService(InMemoryUserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public List<User> findAll() {
        return userStorage.findAll();
    }

    public User create(User user) {
        Validator.userValid(user);
        return userStorage.create(user);
    }

    public User update(User user) {
        Validator.userValid(user);
        return userStorage.update(user);
    }

    public void addFriend(long userId, long friendId) {
        List<User> userList = findAll();
        User user = userStorage.getUserByID(userId);
        User friend = userStorage.getUserByID(friendId);
        user.addFriend(friendId);
        friend.addFriend(userId);
    }

    public void deleteFriend(long userId, long friendId) {
        List<User> userList = findAll();
        User user = userStorage.getUserByID(userId);
        User friend = userStorage.getUserByID(friendId);
        user.deleteFriend(friendId);
        friend.deleteFriend(userId);
    }

    public List<User> commonFriends(long userId, long friendId) {
        User user = userStorage.getUserByID(userId);
        User friend = userStorage.getUserByID(friendId);
        List<Long> userFriends = user.getFriends();
        List<Long> friendFriends = friend.getFriends();
        userFriends.retainAll(friendFriends);
        return userFriends.stream().map(id -> userStorage.getUserByID(id)).collect(Collectors.toList());
    }

    public User getUserById(long id) {
        return userStorage.getUserByID(id);
    }

    public List<User> getUserFriends(long userId) {
        List<Long> friendsList = userStorage.getUserByID(userId).getFriends();
        return friendsList.stream().map(id -> userStorage.getUserByID(id)).collect(Collectors.toList());
    }
}
