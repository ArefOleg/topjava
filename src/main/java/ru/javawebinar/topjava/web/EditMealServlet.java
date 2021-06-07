package ru.javawebinar.topjava.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/edit")
public class EditMealServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String description = request.getParameter("description");
            Integer calories = Integer.parseInt(request.getParameter("calories"));
            String date = request.getParameter("date");

            request.setAttribute("description", description);
            request.setAttribute("calories", calories);
            request.setAttribute("date", date);
            getServletContext().getRequestDispatcher("/editMeal.jsp").forward(request, response);
        }
        catch(Exception ex) {
            getServletContext().getRequestDispatcher("/notfound.jsp").forward(request, response);
        }
    }
}
