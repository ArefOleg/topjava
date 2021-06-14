package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.util.UserMealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MealServlet extends HttpServlet {
    //private static final LoggerWrapper LOG = LoggerWrapper.get(MealServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException, ServletException {
        request.setAttribute("mealList", UserMealsUtil.getWithExceeded(UserMealsUtil.MEAL_LIST, 1500));
        request.getRequestDispatcher("/meals.jsp").forward(request,response);
    }
}
