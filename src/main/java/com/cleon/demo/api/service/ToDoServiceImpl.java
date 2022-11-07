package com.cleon.demo.api.service;

import com.cleon.demo.api.entity.ToDo;
import com.cleon.demo.api.repository.ToDoRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

/**
 * los datos se obtendrán de la URL indicada en el fichero de propiedades
 * de la aplicación, si no se especifica, el valor por defecto será https://jsonplaceholder.typicode.com/todos
 */
@Service
public class ToDoServiceImpl implements ToDoService {

    private ToDoRepository repository;

    public ToDoServiceImpl(ToDoRepository toDoRepository) {
        this.repository = toDoRepository;
    }

    @Override
    public ToDo create(ToDo entity) {
        return repository.save(entity);
    }

    @Override
    public void deleteToDoById(int id) {
        repository.deleteById(Integer.valueOf(id));
    }

    @Override
    public Collection<ToDo> getAll() {
        return (Collection<ToDo>) repository.findAll();
    }

    @Override
    public Collection<ToDo> getByStatus(boolean isCompleted) {
        return repository.findByStatus(isCompleted);
    }

    @Override
    public Collection<ToDo> getByUserId(int userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public Map<Boolean, Long> getStats() {
        return repository.findByStats();
    }

    @Override
    public Collection<String> getTitles() {
        return repository.findTitles();
    }
}
