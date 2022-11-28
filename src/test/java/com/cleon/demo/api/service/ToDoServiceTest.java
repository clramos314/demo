package com.cleon.demo.api.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.cleon.demo.api.entity.ToDo;
import com.cleon.demo.api.repository.ToDoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Collection;

@SpringBootTest
public class ToDoServiceTest {

    @Autowired
    private ToDoService service;

    @MockBean
    private ToDoRepository repository;

    @Test
    @DisplayName("Test getByUserId Success")
    void testGetByUserId() {
        // Setup our mock repository
        ToDo toDo1 = new ToDo(201, 1, "Description for ToDo1", false);
        ToDo toDo2 = new ToDo(202, 1, "Description for ToDo2", false);
        doReturn(Arrays.asList(toDo1, toDo2)).when(repository).findByUserId(1);

        // Execute the service call
        Collection<ToDo> widgets = service.getByUserId(1);

        // Assert the response
        Assertions.assertEquals(2, widgets.size(), "getByUserId should return 2 ToDos");
    }

    @Test
    @DisplayName("Test save ToDo")
    void testSave() {
        // Setup our mock repository
        ToDo toDo = new ToDo(201, 1, "Description for ToDo 1", false);
        doReturn(toDo).when(repository).save(any());

        // Execute the service call
        ToDo returnedToDo = service.create(toDo);

        // Assert the response
        Assertions.assertNotNull(returnedToDo, "The saved ToDo should not be null");
        Assertions.assertEquals(1, returnedToDo.getUserId(), "The User Id should be 1");
    }
}
