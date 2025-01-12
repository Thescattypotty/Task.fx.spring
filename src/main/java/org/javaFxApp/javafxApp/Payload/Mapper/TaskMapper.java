package org.javaFxApp.javafxApp.Payload.Mapper;

import java.time.format.DateTimeFormatter;

import org.javaFxApp.javafxApp.Entity.Task;
import org.javaFxApp.javafxApp.Enum.EStatus;
import org.javaFxApp.javafxApp.Payload.Request.TaskRequest;
import org.javaFxApp.javafxApp.Payload.Response.TaskResponse;
import org.springframework.stereotype.Service;

@Service
public class TaskMapper {
    
    public Task toTask(TaskRequest taskRequest){
        return Task.builder()
            .name(taskRequest.name())
            .description(taskRequest.description())
            .limitDate(taskRequest.limitDate())
            .status(EStatus.valueOf(taskRequest.status()))
            .build();
    }
    public TaskResponse fromTask(Task task){
        return new TaskResponse(
            task.getId().toString(),
            task.getName(),
            task.getDescription(),
            task.getLimitDate().toString(),
            task.getStatus().name(),
            task.getCategory().getId().toString(),
            task.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
            task.getUpdatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
        );
    }
}
