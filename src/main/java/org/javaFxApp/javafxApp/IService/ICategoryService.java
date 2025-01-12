package org.javaFxApp.javafxApp.IService;

import java.util.List;

import org.javaFxApp.javafxApp.Payload.Request.CategoryRequest;
import org.javaFxApp.javafxApp.Payload.Response.CategoryResponse;

public interface ICategoryService {
    void createCategory(CategoryRequest categoryRequest);
    void updateCategory(String id , CategoryRequest categoryRequest);
    void deleteCategory(String id);
    CategoryResponse getCategory(String id);
    List<CategoryResponse> getAllCategories();
}
