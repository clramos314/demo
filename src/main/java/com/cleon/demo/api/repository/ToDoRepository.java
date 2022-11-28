package com.cleon.demo.api.repository;


import java.util.Collection;

import com.cleon.demo.api.entity.ToDo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToDoRepository extends CrudRepository<ToDo, Integer>, ToDoRepositoryExt {

    Collection<ToDo> findByUserId(int userId);

    @Query(value = "SELECT todos.title FROM todos ORDER BY length(todos.title) ASC", nativeQuery = true)
    Collection<String> findTitles();

    @Query(value = "select t.* from todos t where t.completed = :status", nativeQuery = true)
    Collection<ToDo> findByStatus(boolean status);

}


