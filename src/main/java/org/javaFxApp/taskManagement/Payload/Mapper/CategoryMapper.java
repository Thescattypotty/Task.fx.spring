package org.javaFxApp.taskManagement.Payload.Mapper;

import org.javaFxApp.taskManagement.Annotation.Mapper;
import org.javaFxApp.taskManagement.Entity.Category;
import org.javaFxApp.taskManagement.Payload.Request.CategoryRequest;
import org.javaFxApp.taskManagement.Payload.Response.CategoryResponse;

@Mapper
public class CategoryMapper {
    
    public Category toCategory(CategoryRequest categoryRequest) {
        return Category.builder()
            .name(categoryRequest.name())
            .build();
    }

    public CategoryResponse fromCategory(Category category) {
        return new CategoryResponse(
            category.getId().toString(),
            category.getName()
        );
    }
}
