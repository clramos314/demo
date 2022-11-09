package com.cleon.demo.api.controller;

import com.cleon.demo.api.entity.ToDo;
import com.cleon.demo.api.service.ToDoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doReturn;
import static org.mockito.ArgumentMatchers.anyInt;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ToDoControllerTest {

    private static final Logger logger = LogManager.getLogger(ToDoControllerTest.class);

    @MockBean
    private ToDoService toDoService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /odilo/tests/2/user/{userId}")
    void testGetToDosByUserId() throws Exception {
        // Setup our mocked service
        ToDo toDo1 = new ToDo(201, 1, "ToDo title 1", false);
        ToDo toDo2 = new ToDo(202, 1, "ToDo title 2", true);
        doReturn(Lists.newArrayList(toDo1, toDo2)).when(toDoService).getByUserId(anyInt());

        // Execute the GET request
        mockMvc.perform(get("/odilo/tests/2/user/1"))
                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(201)))
                .andExpect(jsonPath("$[0].userId", is(1)))
                .andExpect(jsonPath("$[0].title", is("ToDo title 1")))
                .andExpect(jsonPath("$[0].completed", is(false)))
                .andExpect(jsonPath("$[1].id", is(202)))
                .andExpect(jsonPath("$[1].userId", is(1)))
                .andExpect(jsonPath("$[1].title", is("ToDo title 2")))
                .andExpect(jsonPath("$[1].completed", is(true)));
    }

    @Test
    @DisplayName("POST /odilo/tests/2")
    void testCreateToDo() throws Exception {
        // Setup our mocked service
        ToDo toDoToPost = new ToDo(201, 1, "ToDo title 1", false);
        ToDo toDoToReturn = new ToDo(201, 1, "ToDo title 1", false);
        doReturn(toDoToReturn).when(toDoService).create(toDoToPost);

        // Execute the POST request
        mockMvc.perform(post("/odilo/tests/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(toDoToPost)))

                // Validate the response code and content type
                .andExpect(status().isCreated());

    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
