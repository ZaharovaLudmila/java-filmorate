package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class User {
    private long id;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String login;
    private String name;

    @Past
    private LocalDate birthday;
    private Set<Long> friends = new HashSet<>();

    public void addFriend(long friendId) {
        friends.add(friendId);
    }

    public void deleteFriend(long friendId) {
        friends.remove(friendId);
    }

    public List<Long> getFriends() {
        return new ArrayList<>(friends);
    }
}
