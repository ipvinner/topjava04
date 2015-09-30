package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.BaseEntity;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;

/**
 * Created by ipvinner on 25.06.2015.
 */
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserMealServiceTest {

    @Autowired
    private UserMealService userMealService;


    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testGet() throws Exception {
        UserMeal userMeal = userMealService.get(BaseEntity.START_SEQ + 2, LoggedUser.id());
        MATCHER.assertEquals(USERMEAL1, userMeal);

    }


    @Test
    public void testGetBetweenDates() throws Exception {
        Collection<UserMeal> allMealsBetweenDates = userMealService.getBetweenDates(LocalDateTime.of(2015, 6, 25, 9, 0).toLocalDate(), LocalDateTime.of(2015, 6, 25, 23, 0).toLocalDate(), LoggedUser.id());
        MATCHER.assertCollectionEquals(Arrays.asList(USERMEAL4, USERMEAL5, USERMEAL6), allMealsBetweenDates);
    }

    @Test
    public void testGetBetweenDateTimes() throws Exception {
        Collection<UserMeal> allMealsBetweenDates = userMealService.getBetweenDateTimes(LocalDateTime.of(2015, 6, 25, 9, 0), LocalDateTime.of(2015, 6, 25, 13, 0), LoggedUser.id());
        MATCHER.assertCollectionEquals(Arrays.asList(USERMEAL4), allMealsBetweenDates);
    }

    @Test
    public void testGetAll() throws Exception {
        Collection<UserMeal> allMeals = userMealService.getAll(LoggedUser.id());
        MATCHER.assertCollectionEquals(Arrays.asList(USERMEAL1, USERMEAL2, USERMEAL3, USERMEAL4, USERMEAL5, USERMEAL6), allMeals);
    }

    @Test
    public void testDelete() throws Exception {
        userMealService.delete(100002, LoggedUser.id());
        MATCHER.assertCollectionEquals(Arrays.asList(USERMEAL2, USERMEAL3, USERMEAL4, USERMEAL5, USERMEAL6), userMealService.getAll(LoggedUser.id()));
    }

//    @Test
//    public void testDeleteAll() throws Exception {
//        userMealService.deleteAll(LoggedUser.id());
//        MATCHER.assertCollectionEquals(Arrays.asList(), userMealService.getAll(LoggedUser.id()));
//    }

    @Test
    public void testUpdate() throws Exception {
//        UserMeal updatedMeal = new UserMeal(LocalDateTime.now(), "up meal", 300);
        UserMeal updatedMeal = new UserMeal(USERMEAL2);
        updatedMeal.setDescription("Update meal desc");
        userMealService.update(updatedMeal, LoggedUser.id());
        MATCHER.assertEquals(updatedMeal, userMealService.get(100003, LoggedUser.id()));
    }

    @Test
    public void testSave() throws Exception {
        UserMeal newUserMeal = new UserMeal(LocalDateTime.now(), "еда новая", 200);
        UserMeal userMeal = userMealService.save(newUserMeal, LoggedUser.id());
        newUserMeal.setId(userMeal.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(USERMEAL1, USERMEAL2, USERMEAL3, USERMEAL4, USERMEAL5, USERMEAL6, newUserMeal), userMealService.getAll(LoggedUser.id()));

    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() throws Exception {
        userMealService.delete(1, 20);
    }
}