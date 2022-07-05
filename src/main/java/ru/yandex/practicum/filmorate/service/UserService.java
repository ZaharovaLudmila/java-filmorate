package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
        User user = userStorage.getUserByID(userId);
        User friend = userStorage.getUserByID(friendId);
        user.getFriends().add(friendId);
        friend.getFriends().add(userId);
    }

    public void deleteFriend(long userId, long friendId) {
        User user = userStorage.getUserByID(userId);
        User friend = userStorage.getUserByID(friendId);
        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);
    }

    public List<User> commonFriends(long userId, long friendId) {
        User user = userStorage.getUserByID(userId);
        User friend = userStorage.getUserByID(friendId);
        ArrayList<Long> userFriends = new ArrayList<>(user.getFriends());
        ArrayList<Long> friendFriends = new ArrayList<>(friend.getFriends());
        userFriends.retainAll(friendFriends);
        return userFriends.stream().map(id -> userStorage.getUserByID(id)).collect(Collectors.toList());
    }

    public User getUserById(long id) {
        return userStorage.getUserByID(id);
    }

    public List<User> getUserFriends(long userId) {
        List<Long> friendsList = new ArrayList<>(userStorage.getUserByID(userId).getFriends());
        return friendsList.stream().map(id -> userStorage.getUserByID(id)).collect(Collectors.toList());
    }
}
