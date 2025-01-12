package org.javaFxApp.javafxApp.Payload.Mapper;

import org.javaFxApp.javafxApp.Entity.Category;
import org.javaFxApp.javafxApp.Payload.Request.CategoryRequest;
import org.javaFxApp.javafxApp.Payload.Response.CategoryResponse;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapper {
    
    public Category toCategory(CategoryRequest categoryRequest) {
        return Category.builder()
            .name(categoryRequest.name())
            .build();
    }

    public CategoryResponse toCategoryResponse(Category category) {
        return new CategoryResponse(
            category.getId().toString(),
            category.getName()
        );
    }
}
