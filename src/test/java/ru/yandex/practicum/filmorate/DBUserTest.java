package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.intrf.FriendsStorage;
import ru.yandex.practicum.filmorate.storage.intrf.UserStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DBUserTest {

    private final UserStorage userStorage;
    private final FriendsStorage friendsStorage;
    private User user;

    @BeforeEach
    public void createUser() {
        user = new User();
        user.setName("Jon Snow");
        user.setEmail("snow99mail.ru");
        user.setLogin("blackRaven");
        user.setBirthday(LocalDate.of(1986, 12, 26));
    }

    @Test
    public void testCreateUser() {
        Optional<User> userOptional = userStorage.create(user);

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("id", (long) 1)
                );
    }

    @Test
    public void testUpdateUser() {

        userStorage.create(user);
        user.setId(1);
        user.setLogin("blackRaven86");
        userStorage.update(user);
        Optional<User> userOptional = userStorage.getUserByID(1);
        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("login",
                                "blackRaven86")
                );
    }

    @Test
    public void testDeleteUser() {

        userStorage.create(user);
        user.setId(1);
        userStorage.delete(user);
        Optional<User> userOptional = userStorage.getUserByID(1);
        assertThat(userOptional).isEmpty();
    }

    @Test
    public void testGetUserByID() {
        userStorage.create(user);
        user.setId(1);
        Optional<User> userOptional = userStorage.getUserByID(1);

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("id", (long) 1)
                );
    }

    @Test
    public void testFindAllFilm() {
        userStorage.create(user);
        user.setId(1);
        List<User> users = userStorage.findAll();
        List<User> users2 = new ArrayList<>();
        users2.add(user);
        assertTrue(users.equals(users2), "Список со всеми пользователями не равен ожидаемому");
    }

    @Test
    public void testGetUserFriends() {
        userStorage.create(user);
        user.setId(1);
        User user2 = new User();
        user2.setName("Sam Tarly");
        user2.setEmail("tarly88mail.ru");
        user2.setLogin("blackRaven88");
        user2.setBirthday(LocalDate.of(1988, 9, 5));
        userStorage.create(user2);
        user2.setId(2);
        List<User> friends = new ArrayList<>();
        friends.add(user2);
        friendsStorage.addFriend(1, 2);
        assertTrue(friends.equals(userStorage.getUserFriends(1)),
                "Список с друзьями пользователя не равен ожидаемому");
    }
}
