package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.UserMeal;

import java.util.List;

public interface UserMealRepository {
    UserMeal save(UserMeal userMeal);
    void delete(int id);
    UserMeal get(int id);
    List<UserMeal> getAll();
}
