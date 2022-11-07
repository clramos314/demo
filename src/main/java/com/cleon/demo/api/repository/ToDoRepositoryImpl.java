package com.cleon.demo.api.repository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class ToDoRepositoryImpl implements ToDoRepositoryExt {

    @PersistenceContext
    private EntityManager em;

    public ToDoRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Map<Boolean, Long> findByStats() {

        return em
                .createQuery("SELECT t.completed AS completed, count(t) AS countered FROM todos t GROUP BY t.completed", Tuple.class)
                .getResultStream()
                .collect(
                        Collectors.toMap(
                                tuple -> (Boolean) tuple.get("completed"),
                                tuple -> ((Number) tuple.get("countered")).longValue()
                        )
                );
    }
}
