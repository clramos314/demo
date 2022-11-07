package com.cleon.demo.api.repository;

import java.util.Map;

public interface ToDoRepositoryExt {
    Map<Boolean, Long> findByStats();
}
