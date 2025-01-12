package org.javaFxApp.javafxApp.Service;

import java.util.List;
import java.util.UUID;

import org.javaFxApp.javafxApp.Entity.Category;
import org.javaFxApp.javafxApp.IService.ICategoryService;
import org.javaFxApp.javafxApp.Payload.Mapper.CategoryMapper;
import org.javaFxApp.javafxApp.Payload.Request.CategoryRequest;
import org.javaFxApp.javafxApp.Payload.Response.CategoryResponse;
import org.javaFxApp.javafxApp.Repository.CategoryRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional
    public void createCategory(CategoryRequest categoryRequest) {
        categoryRepository.save(categoryMapper.toCategory(categoryRequest));
    }

    @Override
    @Transactional
    public void updateCategory(String id, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(UUID.fromString(id))
            .orElseThrow(() -> new RuntimeException("Category not found"));
        Category updatedCat = categoryMapper.toCategory(categoryRequest);
        updatedCat.setId(category.getId());
        updatedCat.setTasks(category.getTasks());
        categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void deleteCategory(String id) {
        if(categoryRepository.existsById(UUID.fromString(id))){
            categoryRepository.deleteById(UUID.fromString(id));
        }else{
            throw new RuntimeException("Category not found");
        }
    }

    @Override
    public CategoryResponse getCategory(String id) {
        return categoryRepository.findById(UUID.fromString(id))
            .map(categoryMapper::fromCategory)
            .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
            .stream()
            .map(categoryMapper::fromCategory)
            .toList();
    }
    
}
