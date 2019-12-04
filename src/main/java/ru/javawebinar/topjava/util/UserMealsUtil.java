package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 800),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 16, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 300),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceededbyCycle(mealList, LocalTime.of(17, 0), LocalTime.of(20, 0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceededbyCycle(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> dailyMealMap = new HashMap<>();

        for (UserMeal meal : mealList) {
            dailyMealMap.put(meal.getDateTime().toLocalDate(),
                    dailyMealMap.getOrDefault(meal.getDateTime().toLocalDate(), 0)
                            + meal.getCalories());
        }

        List<UserMealWithExceed> userMealWithExceeds = new ArrayList<>();
        for (UserMeal meal : mealList) {
            if (TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                userMealWithExceeds.add(new UserMealWithExceed(
                        meal.getDateTime(),
                        meal.getDescription(),
                        meal.getCalories(),
                        dailyMealMap.get(meal.getDateTime().toLocalDate()) > caloriesPerDay));
            }
        }

        userMealWithExceeds.forEach(userMealWithExceed -> System.out.println(userMealWithExceed.toString()));
        return userMealWithExceeds;
    }
}
