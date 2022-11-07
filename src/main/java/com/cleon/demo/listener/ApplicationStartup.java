package com.cleon.demo.listener;

import com.cleon.demo.api.entity.ToDo;
import com.cleon.demo.api.service.ToDoService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
@Slf4j
@Profile("!h2")
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    ToDoService toDoService;

    @Value( "${json.data}")
    private String pathJson;

    /**
     * This event is executed as late as conceivably possible to indicate that
     * the application is ready to service requests.
     *
     * Currently, read json from resources and write to db when profile is NOT h2
     */
    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<ToDo>> typeReference = new TypeReference<List<ToDo>>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream(pathJson);
        try {
            List<ToDo> toDos = mapper.readValue(inputStream,typeReference);
            if(!toDos.isEmpty()) {
                toDos.forEach(toDo -> toDoService.create(toDo));
                log.info("ToDos Saved!");
            }
        } catch (IOException e){
            log.warn("Unable to save ToDos: " + e.getMessage());
        }
    }

}

