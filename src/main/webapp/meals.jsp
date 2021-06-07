<%@ page import="ru.javawebinar.topjava.model.UserMealWithExcess" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="ru.javawebinar.topjava.model.UserMealWithExcess" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.Locale" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Meal List</title>
</head>
<body>
<h1>Displaying Meal List</h1>
<table border="1" width="500" align="center">
    <tr>
        <th><b>Date</b></th>
        <th><b>Desctiption</b></th>
        <th><b>Calories</b></th>
    </tr>
    <%DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.US);%>
    <%
        List<UserMealWithExcess> meals =
                (List<UserMealWithExcess>) request.getAttribute("meals");
        for (UserMealWithExcess meal : meals) {
    %>
    <%--  <tr <% if(!meal.getExcess().get()){ %> style="color:#ff0400" <%} else %> style="color:#11bf0b">  --%>
    <tr  <% if (!meal.getExcess().get()) { %> style="color:#ff0400" <%} else %> style="color:#11bf0b" <%%>>
        <td><%=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(meal.getDateTime())%>
        </td>
        <td><%=meal.getDescription()%>
        </td>
        <td><%=meal.getCalories()%>
        </td>
        <td><%=meal.getId()%>
        </td>
        <td><a href="http://localhost:8080/topjava_war_exploded/edit?descrition=<%meal.getDescription();%>&date
        =<%meal.getDateTime();%>&calories=<%meal.getCalories();%>"
        >Edit</a></td>
        <td>Delete</td>
    </tr>
    <%}%>
</table>
<hr/>
</body>
</html>