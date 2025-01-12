package org.javaFxApp.taskManagement.Payload.Mapper;

import java.time.format.DateTimeFormatter;

import org.javaFxApp.taskManagement.Annotation.Mapper;
import org.javaFxApp.taskManagement.Entity.Task;
import org.javaFxApp.taskManagement.Enum.EStatus;
import org.javaFxApp.taskManagement.Payload.Request.TaskRequest;
import org.javaFxApp.taskManagement.Payload.Response.TaskResponse;

@Mapper
public class TaskMapper {
    
    public Task toTask(TaskRequest taskRequest){
        return Task.builder()
            .name(taskRequest.name())
            .description(taskRequest.description())
            .limitDate(taskRequest.limitDate())
            .status(taskRequest.status() != null ? EStatus.valueOf(taskRequest.status()) : EStatus.PENDING)
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
