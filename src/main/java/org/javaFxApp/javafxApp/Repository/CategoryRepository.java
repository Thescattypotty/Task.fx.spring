package org.javaFxApp.javafxApp.Repository;

import java.util.UUID;

import org.javaFxApp.javafxApp.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Category findByName(String name);
}
