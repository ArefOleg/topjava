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
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 1200),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 70),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 9, 0), "Завтрак", 4000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 15, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );


        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(13, 1), 2000); // объявление
        System.out.println(mealsTo.toString());
        /*for(UserMealWithExcess userMealWithExcess: mealsTo)
        {
            System.out.println(mealsTo.toString());
        }*/


        //   filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(20, 0), 3000);
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> listExcess = new ArrayList<>();
        for(UserMeal usermeal: meals)
        {
            if(usermeal.getDateTime().toLocalTime().compareTo(startTime) > 0 && usermeal.getDateTime().toLocalTime().compareTo(endTime) < 0) listExcess.add(new UserMealWithExcess(usermeal.getDateTime(),usermeal.getDescription(),usermeal.getCalories(), CheckExcessList(meals, usermeal.getDateTime(), caloriesPerDay)));
        }
        return listExcess;
    }

    public static boolean CheckExcessList(List<UserMeal> meals, LocalDateTime Time, int Calories)
    {
        boolean checkExcess;
        int sum = 0;
        int Day = Time.getDayOfMonth();
        int Month = Time.getMonthValue();
        int Year = Time.getYear();
        for(UserMeal usermeal: meals)
        {
            if(usermeal.getDateTime().getDayOfMonth() == Day && usermeal.getDateTime().getMonthValue() == Month && usermeal.getDateTime().getYear() == Year)
                sum = sum + usermeal.getCalories();
        }
        if (Calories > sum)
        {
            checkExcess = false;
        }
        else  checkExcess = true;
        return checkExcess;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        meals.stream().filter(p->((p.getDateTime()).toLocalTime()).compareTo(startTime) > 0 && ((p.getDateTime()).toLocalTime()).compareTo(endTime) < 0).forEach(p->System.out.println("dateTime = " + (p.getDateTime()).toLocalTime()  + ", description = " + p.getDescription() + ", calories = " + p.getCalories() + ", excess = " + CheckExcessStream(meals, p.getDateTime(), caloriesPerDay)));
        return null;
    }

    public static boolean CheckExcessStream(List<UserMeal> meals, LocalDateTime Time, int Calories)
    {
        boolean checkExcess;
        int Day = Time.getDayOfMonth();
        int Month = Time.getMonthValue();
        int Year = Time.getYear();
        Stream<List> UM = Stream.of(meals);
        int sum = meals.stream().filter(p->p.getDateTime().getDayOfMonth() == Day && p.getDateTime().getMonthValue() == Month && p.getDateTime().getYear() == Year).reduce(0,(tempExcessResult, meal) -> tempExcessResult + meal.getCalories(), Integer::sum);
        System.out.println(sum);
        if (Calories > sum)
        {
            checkExcess = false;
        }
        else  checkExcess = true;
        return checkExcess;
    }


   /* public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        return null;
    }*/
}
