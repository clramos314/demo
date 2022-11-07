package com.cleon.demo.api.controller;

import com.cleon.demo.api.dto.ToDoDTO;
import com.cleon.demo.api.entity.ToDo;
import com.cleon.demo.api.service.ToDoService;
import com.cleon.demo.job.SchedulerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/odilo/tests")
public class ToDoController {

    @Autowired
    protected ToDoService toDoService;

    private static final String SCHEDULED_TASKS = "scheduledTasks";

    @Autowired
    private ScheduledAnnotationBeanPostProcessor postProcessor;

    @Autowired
    private SchedulerConfig schedulerConfiguration;

    /**
     * Devuelve una lista de los jobs que se estén ejecutando
     * @return
     */
    @GetMapping("/1")
    public ResponseEntity<String> doJobList(){
        Set<ScheduledTask> setTasks = postProcessor.getScheduledTasks();
        if (!setTasks.isEmpty()) {
            return ResponseEntity.ok().body(setTasks.toString());
        } else {
            return ResponseEntity.ok().body("Currently no scheduler tasks are running");
        }
    }

    /**
     * Cancela la ejecución de los jobs
     * @return
     */
    @PutMapping("/1")
    public ResponseEntity<List<Runnable>> doCancelJobList(){
        postProcessor.destroy();
        postProcessor.postProcessBeforeDestruction(schedulerConfiguration, SCHEDULED_TASKS);
        return ResponseEntity.ok().build();
    }

    /**
     * Crea un nuevo ToD0
     * @param toDoDto
     * @return
     */
    @PostMapping("/2")
    public ResponseEntity<ToDo> doCreate(@Valid @RequestBody ToDoDTO toDoDto){
        ToDo toDoDb = toDoService.create(toDoDto.toToDo());
        return ResponseEntity.status(HttpStatus.CREATED).body(toDoDb);
    }

    /**
     * Elimina un ToD0 de la lista
     * @param toDoId
     * @return
     */
    @DeleteMapping("/2/{id}")
    public ResponseEntity<Void> doDelete(@PathVariable(name = "id") int toDoId){
        toDoService.deleteToDoById(toDoId);
        return ResponseEntity.accepted().build();
    }

    /**
     * Devuelve todos los ToD0. Además. acepta un parámetro en la query opcional que indica si se muestran solo los
     * completados o los no completados
     * @param status
     * @return
     */
    @GetMapping("/2")
    public ResponseEntity<Collection<ToDo>> doList(@RequestParam(name = "status") Optional<Boolean> status){
        // devolverá los no completados en caso de no ser indicado
        return ResponseEntity.ok().body(toDoService.getByStatus(status.orElseGet(() -> false)));
    }

    /**
     * Devuelve todos los ToD0 del usuario especificado
     * @return
     */
    @GetMapping("/2/user/{userid}")
    public ResponseEntity<Collection<ToDo>> doListByUserId(@PathVariable(name = "userid") int userId){
        return ResponseEntity.ok().body(toDoService.getByUserId(userId));
    }

    /**
     * Devuelve las estadísticas de número de elementos completados y pendientes
     * @return
     */
    @GetMapping("/2/stats")
    public ResponseEntity<Map<Boolean, Long>> doListStats(){
        return ResponseEntity.ok().body(toDoService.getStats());
    }

    /**
     * Devuelve los títulos de todos los ToD0
     * @return
     */
    @GetMapping("/2/titles")
    public ResponseEntity<Collection<String>> doListTitles(){
        return ResponseEntity.ok().body(toDoService.getTitles());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
