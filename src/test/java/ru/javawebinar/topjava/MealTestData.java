package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {

    public static final int MEAL_ID2 = START_SEQ +2;

    public static final ModelMatcher<UserMeal, String> MATCHER = new ModelMatcher<>(UserMeal::toString);

    public static final UserMeal USERMEAL1 = new UserMeal(100002, LocalDateTime.of(2015, 6, 24, 9, 0), "завтрак", 400);
    public static final UserMeal USERMEAL2 = new UserMeal(100003, LocalDateTime.of(2015, 6, 24, 12, 0), "обед", 400);
    public static final UserMeal USERMEAL3 = new UserMeal(100004, LocalDateTime.of(2015, 6, 24, 22, 0), "ужин", 100);

    public static final UserMeal USERMEAL4 = new UserMeal(100005, LocalDateTime.of(2015, 6, 25, 9, 0), "завтрак", 200);
    public static final UserMeal USERMEAL5 = new UserMeal(100006, LocalDateTime.of(2015, 6, 25, 13, 0), "обед", 700);
    public static final UserMeal USERMEAL6 = new UserMeal(100007, LocalDateTime.of(2015, 6, 25, 19, 0), "ужин", 400);
}
