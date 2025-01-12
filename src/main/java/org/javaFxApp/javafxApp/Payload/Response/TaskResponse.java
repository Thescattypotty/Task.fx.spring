package org.javaFxApp.javafxApp.Payload.Response;

public record TaskResponse(
    String id,
    String name,
    String description,
    String limitDate,
    String status,
    String categoryId,
    String createdAt,
    String updatedAt
) {
    
}
