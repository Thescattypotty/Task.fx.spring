package org.javaFxApp.taskManagement.IService;

import java.util.List;

import org.javaFxApp.taskManagement.Payload.Request.CategoryRequest;
import org.javaFxApp.taskManagement.Payload.Response.CategoryResponse;

public interface ICategoryService {
    void createCategory(CategoryRequest categoryRequest);
    void updateCategory(String id , CategoryRequest categoryRequest);
    void deleteCategory(String id);
    CategoryResponse getCategory(String id);
    List<CategoryResponse> getAllCategories();
}
