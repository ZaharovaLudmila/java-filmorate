package ru.yandex.practicum.filmorate.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.intrf.FriendsStorage;

@Component
public class FriendsDao implements FriendsStorage {

    private final JdbcTemplate jdbcTemplate;
    private final Logger log = LoggerFactory.getLogger(FriendsDao.class);

    public FriendsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addFriend(long userId, long friendId) {
        String sqlQuery = "merge into FRIENDS(USER_ID, FRIEND_ID, CONFIRMED) values (?, ?, ?)";
        boolean confirmed = checkFriendshipStatus(userId, friendId);
        jdbcTemplate.update(sqlQuery, userId, friendId, confirmed);
        if(confirmed) {
            jdbcTemplate.update(sqlQuery, friendId, userId, true);
        }
    }

    @Override
    public void deleteFriend(long userId, long friendId) {
        String sqlQuery = "delete from FRIENDS where USER_ID = ? AND FRIEND_ID = ?";
        if(jdbcTemplate.update(sqlQuery, userId, friendId) > 0) {
            if (checkFriendshipStatus(userId, friendId)) {
                addFriend(friendId, userId);
            }
        } else {
            throw new RuntimeException("Не удалось удалить друга с id " + friendId + " у пользователя с id " + userId);
        }
    }

    @Override
    public boolean checkFriendshipStatus (long userId, long friendId) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("select * from FRIENDS where FRIEND_ID = ? AND USER_ID = ?",
                userId, friendId);
        if(userRows.next()) {
            return true;
        }
        return false;
    }
}
