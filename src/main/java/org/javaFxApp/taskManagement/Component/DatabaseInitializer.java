package org.javaFxApp.taskManagement.Component;

import java.time.LocalDate;
import java.util.List;

import org.javaFxApp.taskManagement.Entity.Category;
import org.javaFxApp.taskManagement.Entity.Task;
import org.javaFxApp.taskManagement.Enum.EStatus;
import org.javaFxApp.taskManagement.Repository.CategoryRepository;
import org.javaFxApp.taskManagement.Repository.TaskRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner{
    
    private final CategoryRepository categoryRepository;
    private final TaskRepository taskRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {   
        if(categoryRepository.count() > 0){
            return;
        }     
        categoryRepository.saveAll(
            List.of(
                Category.builder().name("Work").build(),
                Category.builder().name("Home").build(),
                Category.builder().name("Study").build(),
                Category.builder().name("Sport").build(),
                Category.builder().name("Hobby").build(),
                Category.builder().name("Other").build()
            )
        );
        if(taskRepository.count() > 0){
            return;
        }
        taskRepository.saveAll(
            List.of(
                Task.builder().name("Task 1").status(EStatus.PENDING).limitDate(LocalDate.now().plusDays(1)).category(categoryRepository.findByName("Work")).build(),
                Task.builder().name("Task 2").status(EStatus.PENDING).limitDate(LocalDate.now().plusDays(2)).category(categoryRepository.findByName("Home")).build(),
                Task.builder().name("Task 3").status(EStatus.PENDING).limitDate(LocalDate.now().plusDays(3)).category(categoryRepository.findByName("Study")).build(),
                Task.builder().name("Task 4").status(EStatus.PENDING).limitDate(LocalDate.now().plusDays(4)).category(categoryRepository.findByName("Sport")).build(),
                Task.builder().name("Task 5").status(EStatus.PENDING).limitDate(LocalDate.now().plusDays(5)).category(categoryRepository.findByName("Hobby")).build(),
                Task.builder().name("Task 6").status(EStatus.PENDING).limitDate(LocalDate.now().plusDays(6)).category(categoryRepository.findByName("Other")).build()
            )
        );


    }
}
