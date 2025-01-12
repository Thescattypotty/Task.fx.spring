package org.javaFxApp.taskManagement.IService;

import java.util.List;

import org.javaFxApp.taskManagement.Payload.Request.TaskRequest;
import org.javaFxApp.taskManagement.Payload.Response.TaskResponse;

public interface ITaskService {
    void createTask(TaskRequest taskRequest);
    void updateTask(String id, TaskRequest taskRequest);
    void deleteTask(String id);
    TaskResponse getTask(String id);
    List<TaskResponse> getTasksByCategory(String categoryId);
    List<TaskResponse> getTasks();
    
}
