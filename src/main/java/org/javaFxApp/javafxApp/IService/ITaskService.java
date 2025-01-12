package org.javaFxApp.javafxApp.IService;

import java.util.List;

import org.javaFxApp.javafxApp.Payload.Request.TaskRequest;
import org.javaFxApp.javafxApp.Payload.Response.TaskResponse;

public interface ITaskService {
    void createTask(TaskRequest taskRequest);
    void updateTask(String id, TaskRequest taskRequest);
    void deleteTask(String id);
    TaskResponse getTask(String id);
    List<TaskResponse> getTasksByCategory(String categoryId);
    List<TaskResponse> getTasks();
    
}
