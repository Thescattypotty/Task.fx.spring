package org.javaFxApp.taskManagement.Repository;

import java.util.UUID;

import org.javaFxApp.taskManagement.Entity.Task;
import org.javaFxApp.taskManagement.Enum.EStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID>{
    List<Task> findByCategoryId(UUID categoryId);
    List<Task> findByLimitDateBetween(LocalDate start, LocalDate end);
    List<Task> findByStatus(EStatus status);
}
