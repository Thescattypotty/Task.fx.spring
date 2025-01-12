package org.javaFxApp.taskManagement.Payload.Request;

import java.time.LocalDate;

public record TaskRequest(
    String name,
    String description,
    LocalDate limitDate,
    String status,
    String categoryId
) {
    
}
