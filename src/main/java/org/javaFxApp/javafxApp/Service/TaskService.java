package org.javaFxApp.javafxApp.Service;

import java.util.List;
import java.util.UUID;

import org.javaFxApp.javafxApp.Entity.Task;
import org.javaFxApp.javafxApp.Enum.EStatus;
import org.javaFxApp.javafxApp.IService.ITaskService;
import org.javaFxApp.javafxApp.Payload.Mapper.TaskMapper;
import org.javaFxApp.javafxApp.Payload.Request.TaskRequest;
import org.javaFxApp.javafxApp.Payload.Response.TaskResponse;
import org.javaFxApp.javafxApp.Repository.CategoryRepository;
import org.javaFxApp.javafxApp.Repository.TaskRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskService implements ITaskService{

    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;
    private final TaskMapper taskMapper;

    @Override
    @Transactional
    public void createTask(TaskRequest taskRequest) {
        Task task = taskMapper.toTask(taskRequest);
        if(taskRequest.categoryId() != null){
            task.setCategory(categoryRepository.findById(UUID.fromString(taskRequest.categoryId()))
                .orElseThrow(() -> new RuntimeException("Category not found")));
        }else{
            throw new RuntimeException("Category is required");
        }
        taskRepository.save(task);
    }

    @Override
    @Transactional
    public void updateTask(String id, TaskRequest taskRequest) {
        Task task = taskRepository.findById(UUID.fromString(id))
            .orElseThrow(() -> new RuntimeException("Task not found"));
        task.setName(taskRequest.name());
        task.setDescription(taskRequest.description());
        task.setLimitDate(taskRequest.limitDate());
        task.setStatus(taskRequest.status() != null ? EStatus.valueOf(taskRequest.status()) : EStatus.PENDING);
        if(taskRequest.categoryId() != task.getCategory().getId().toString()){
            task.setCategory(categoryRepository.findById(UUID.fromString(taskRequest.categoryId()))
                .orElseThrow(() -> new RuntimeException("Category not found")));
        }
        taskRepository.save(task);
    }

    @Override
    @Transactional
    public void deleteTask(String id) {
        if(taskRepository.existsById(UUID.fromString(id))){
            taskRepository.deleteById(UUID.fromString(id));
        }else{
            throw new RuntimeException("Task not found");
        }
    }

    @Override
    public TaskResponse getTask(String id) {
        return taskRepository.findById(UUID.fromString(id))
            .map(taskMapper::fromTask)
            .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    @Override
    public List<TaskResponse> getTasksByCategory(String categoryId) {
        return taskRepository.findByCategoryId(UUID.fromString(categoryId))
            .stream()
            .map(taskMapper::fromTask)
            .toList();
    }

    @Override
    public List<TaskResponse> getTasks() {
        return taskRepository.findAll()
            .stream()
            .map(taskMapper::fromTask)
            .toList();
    }
    
}
