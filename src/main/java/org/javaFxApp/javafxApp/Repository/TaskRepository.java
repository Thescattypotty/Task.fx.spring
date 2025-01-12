package org.javaFxApp.javafxApp.Repository;

import java.util.UUID;

import org.javaFxApp.javafxApp.Entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import org.javaFxApp.javafxApp.Enum.EStatus;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID>{
    List<Task> findByCategoryId(UUID categoryId);
    List<Task> findByLimitDateBetween(LocalDate start, LocalDate end);
    List<Task> findByStatus(EStatus status);
}
