package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class MealServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request,
                                  HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Meals</title>");
            out.println("</head>");
            out.println("<body>");
            List<UserMeal> meals = Arrays.asList(
                    new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                    new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                    new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                    new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                    new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                    new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                    new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
            );

            List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);

            request.setAttribute("meals", mealsTo);

            RequestDispatcher rd =
                    request.getRequestDispatcher("/meals.jsp");

            rd.forward(request, response);
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        processRequest(request, response);
    }

    public List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> listExcess = new ArrayList<>();
        final Map<LocalDate, Integer> caloriesSumByDate = new HashMap<>();
        final Map<LocalDate, AtomicBoolean> exceededMap = new HashMap<>();
        final Map<LocalDateTime, AtomicInteger> integerMap = new HashMap<>();
        AtomicInteger idCounter = new AtomicInteger();
        meals.forEach(meal -> {
                    AtomicInteger wrapInteger = integerMap.computeIfAbsent(meal.getDateTime(), integer -> new AtomicInteger(idCounter.get())); //Не знаю как красиво написать лямбдой и без счетчика, ткните плз совет
                    AtomicBoolean wrapBoolean = exceededMap.computeIfAbsent(meal.getDateTime().toLocalDate(), date -> new AtomicBoolean());
                    Integer dailyCalories = caloriesSumByDate.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum);

                    if (dailyCalories > caloriesPerDay) {
                        wrapBoolean.set(true);
                    }
                    idCounter.incrementAndGet();
                    listExcess.add(createWithExceeded(meal, wrapBoolean, wrapInteger));
                    //wrapInteger.getAndAdd(1);
                }
        );
        return listExcess;
    }

    public UserMealWithExcess createWithExceeded(UserMeal meal, AtomicBoolean exceeded, AtomicInteger Id) {
        return new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceeded, Id);
    }

    public List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        return null;
    }


}

