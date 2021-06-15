package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class UserMealsUtil {
    public static final List<UserMeal> MEAL_LIST = Arrays.asList(
            new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
    );

    public static final Collection<UserMeal> MEAL_LIST_COL = Arrays.asList(
            new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
    );

    public static void main(String[] args) {
     //   List<UserMealWithExcess> filteredMealsWithExceeded = filteredByCycles(MEAL_LIST, LocalTime.of(7,0), LocalTime.of(21,0), 2000);
        Collection<UserMealWithExcess> filteredByCyclesCollection = filteredByCyclesCollection(MEAL_LIST_COL, LocalTime.of(7,0), LocalTime.of(21,0), 2000);
    }



    public static Collection<UserMealWithExcess> getWithExceededCollection(Collection<UserMeal> meals, int caloriesPerDay){
        return filteredByCyclesCollection(meals, LocalTime.MIN, LocalTime.MAX, caloriesPerDay);
    }

    public static Collection<UserMealWithExcess> filteredByCyclesCollection(Collection<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Collection<UserMealWithExcess> listExcess = new ArrayList<>();
        final Map<LocalDate, Integer> caloriesSumByDate = new HashMap<>();
        final Map<LocalDate, AtomicBoolean> exceededMap = new HashMap<>();
        meals.forEach(meal -> {
                    AtomicBoolean wrapBoolean = exceededMap.computeIfAbsent(meal.getDateTime().toLocalDate(), date -> new AtomicBoolean());
                    Integer dailyCalories = caloriesSumByDate.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum);
                    if (dailyCalories > caloriesPerDay) {
                        wrapBoolean.set(true);
                    }
                    listExcess.add(createWithExceeded(meal, wrapBoolean));
                }
        );
        return listExcess;
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> listExcess = new ArrayList<>();
        final Map<LocalDate, Integer> caloriesSumByDate = new HashMap<>();
        final Map<LocalDate, AtomicBoolean> exceededMap = new HashMap<>();
        meals.forEach(meal -> {
            AtomicBoolean wrapBoolean = exceededMap.computeIfAbsent(meal.getDateTime().toLocalDate(), date -> new AtomicBoolean());
            Integer dailyCalories = caloriesSumByDate.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum);
            if (dailyCalories > caloriesPerDay) {
                wrapBoolean.set(true);
            }
            listExcess.add(createWithExceeded(meal, wrapBoolean));
        }
        );
        return listExcess;
    }

    public static List<UserMealWithExcess> getWithExceeded(List<UserMeal> meals, int caloriesPerDay){
        return filteredByCycles(meals, LocalTime.MIN, LocalTime.MAX, caloriesPerDay);
    }

    public static UserMealWithExcess createWithExceeded(UserMeal meal, AtomicBoolean exceeded) {
        return new UserMealWithExcess(meal.getId(),meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceeded);
    }


}
