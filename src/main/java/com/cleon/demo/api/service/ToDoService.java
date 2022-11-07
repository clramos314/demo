package com.cleon.demo.api.service;


import com.cleon.demo.api.entity.ToDo;

import java.util.Collection;
import java.util.Map;

public interface ToDoService {
    ToDo create(ToDo item);

    void deleteToDoById(int id);

    Collection<ToDo> getAll();

    Collection<ToDo> getByStatus(boolean isCompleted);

    Collection<ToDo> getByUserId(int userId);

    Map<Boolean, Long> getStats();

    Collection<String> getTitles();
}
