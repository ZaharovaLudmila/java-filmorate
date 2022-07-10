package ru.yandex.practicum.filmorate.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.intrf.MPAStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class MPADao implements MPAStorage {

    private final JdbcTemplate jdbcTemplate;
    private final Logger log = LoggerFactory.getLogger(MPADao.class);

    public MPADao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<MPA> findAll() {
        String sqlQuery = "select * from MOTION_PICTURE_ASSOCIATIONS";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> makeMPA(rs, rowNum));
    }

    @Override
    public Optional<MPA> getMPAByID(int id) {
        String sqlQuery = "select * from MOTION_PICTURE_ASSOCIATIONS where MPA_ID = ?";
        List<MPA> MPARows = jdbcTemplate.query(sqlQuery, (rs, rowNum) ->
                makeMPA(rs, rowNum), id);
        if (MPARows.size() > 0) {
            MPA MPA = MPARows.get(0);
            log.info("Найден рейтинг MPA по id: {} {}", MPA.getName(), id);
            return Optional.of(MPA);
        } else {
            return Optional.empty();
        }
    }

    private MPA makeMPA(ResultSet rs, int rowNum) throws SQLException {
        return new MPA(rs.getInt("MPA_ID"), rs.getString("MPA_NAME"));
    }
}
