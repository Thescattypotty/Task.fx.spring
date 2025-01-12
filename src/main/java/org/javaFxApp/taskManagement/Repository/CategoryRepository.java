package org.javaFxApp.taskManagement.Repository;

import java.util.UUID;

import org.javaFxApp.taskManagement.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Category findByName(String name);
}
