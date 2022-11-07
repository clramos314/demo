package com.cleon.demo.api.dto;

import com.cleon.demo.api.entity.ToDo;
import lombok.Data;

import javax.validation.constraints.*;


@Data
public class ToDoDTO {

    private int id;

    @Positive
    private int userId;

    @NotEmpty
    private String title;

    private boolean completed;

    public ToDo toToDo() {
        return new ToDo()
                .setUserId(userId)
                .setTitle(title)
                .setCompleted(completed);
    }
}
