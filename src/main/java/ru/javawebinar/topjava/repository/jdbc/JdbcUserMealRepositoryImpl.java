package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * User: gkislin
 * Date: 26.08.2014
 */

@Repository
public class JdbcUserMealRepositoryImpl implements UserMealRepository {

    //    private static final BeanPropertyRowMapper<UserMeal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(UserMeal.class);
    private static final RowMapper<UserMeal> ROW_MAPPER = (ResultSet rs, int rowNum) -> new UserMeal(rs.getInt("id"),
            rs.getTimestamp("dateTime").toLocalDateTime(), rs.getString("description"), rs.getInt("calories"));

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertMeal;

    @Autowired
    public JdbcUserMealRepositoryImpl(DataSource dataSource) {
        this.insertMeal = new SimpleJdbcInsert(dataSource).withTableName("MEALS").usingGeneratedKeyColumns("id");
    }

    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", userMeal.getId())
                .addValue("user_id", userId)
                .addValue("datetime", Timestamp.valueOf(userMeal.getDateTime()))
                .addValue("description", userMeal.getDescription())
                .addValue("calories", userMeal.getCalories());

        if (userMeal.isNew()) {
            Number newKey = insertMeal.executeAndReturnKey(map);
            userMeal.setId(newKey.intValue());
        } else {
            if(namedParameterJdbcTemplate.update("UPDATE meals SET datetime=:datetime, description=:description, calories=:calories" +
                    " WHERE id=:id and user_id=:user_id", map) == 0){
                return null;
            }
        }
        return userMeal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE FROM meals WHERE id=? and user_id=?", id, userId) != 0;
    }



//    @Override
//    public void deleteAll(int userId) {
//        jdbcTemplate.update("DELETE FROM meals WHERE user_id=?", userId);
//    }
//
    @Override
    public UserMeal get(int id, int userId) {
        List<UserMeal> userMeals = jdbcTemplate.query(
                "SELECT id, datetime, description, calories FROM meals WHERE id = ? AND user_id = ?", ROW_MAPPER, id, userId);
        if(userMeals.isEmpty()){
            return null;
        }else {
            return jdbcTemplate.queryForObject(
                    "SELECT id, datetime, description, calories FROM meals WHERE id = ? AND user_id = ?", ROW_MAPPER, id, userId);
        }

    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return jdbcTemplate.query(
                "SELECT id, datetime, description, calories FROM meals WHERE user_id = ?", ROW_MAPPER, userId);
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return jdbcTemplate.query("SELECT id, datetime, description, calories FROM meals WHERE dateTime >= ? AND dateTime < ? AND user_id = ? ORDER BY dateTime ASC",
                ROW_MAPPER, Timestamp.valueOf(startDate), Timestamp.valueOf(endDate), userId);
    }
}
