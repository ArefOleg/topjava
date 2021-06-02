package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 9, 0), "Завтрак", 1200),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 70),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 8, 0), "Завтрак", 4000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 15, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410),
                new UserMeal(LocalDateTime.of(2021, Month.JANUARY, 25, 5, 22), "Ночник", 400),
                new UserMeal(LocalDateTime.of(2021, Month.DECEMBER, 25, 3, 22), "Ночник", 500),
                new UserMeal(LocalDateTime.of(2021, Month.DECEMBER, 25, 14, 22), "Обед", 500),
                new UserMeal(LocalDateTime.of(2021, Month.DECEMBER, 25, 17, 22), "Ужин", 500)
        );
        TimeUtil tu = new TimeUtil();
        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(3, 0), LocalTime.of(5, 30), 1600, tu); // объявление
        System.out.println(mealsTo.toString());
        filteredByStreams(meals, LocalTime.of(10, 0), LocalTime.of(18, 0), 1600, tu);
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay, TimeUtil tu) {
        List<UserMealWithExcess> listExcess = new ArrayList<>();
        int sum = 0;

        for (UserMeal usermeal : meals) {
            if (tu.isBetweenHalfOpen(usermeal.getDateTime().toLocalTime(), startTime, endTime) == true) {
                for (UserMeal usermeal2 : meals) {
                    if ((usermeal.getDateTime()).toLocalDate().compareTo((usermeal2.getDateTime()).toLocalDate()) == 0) {
                        sum = sum + usermeal2.getCalories();
                    }
                }
                listExcess.add(new UserMealWithExcess(
                        usermeal.getDateTime(),
                        usermeal.getDescription(),
                        usermeal.getCalories(),
                        sum < caloriesPerDay
                ));
                sum = 0;
            }
        }
        return listExcess;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay, TimeUtil tu) {
        meals.stream().filter(p -> tu.isBetweenHalfOpen((p.getDateTime()).toLocalTime(), startTime, endTime) == true)
                .forEach(p -> System.out.println(
                        "dateTime = " + (p.getDateTime()).toLocalTime() +
                                ", description = " + p.getDescription() +
                                ", calories = " + p.getCalories() +
                                ", excess = " + ((meals.stream().
                                filter(s -> s.getDateTime().toLocalDate().compareTo(p.getDateTime().toLocalDate()) == 0).
                                reduce(0, (x, y) -> x + y.getCalories(), Integer::sum)) < caloriesPerDay)));
        return null;
    }

}
