package org.javaFxApp.taskManagement.Controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.javaFxApp.taskManagement.Annotation.FxController;
import org.javaFxApp.taskManagement.Enum.EStatus;
import org.javaFxApp.taskManagement.Payload.Request.CategoryRequest;
import org.javaFxApp.taskManagement.Payload.Request.TaskRequest;
import org.javaFxApp.taskManagement.Payload.Response.CategoryResponse;
import org.javaFxApp.taskManagement.Service.CategoryService;
import org.javaFxApp.taskManagement.Service.TaskService;
import org.springframework.context.ApplicationContext;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@FxController
@Log4j2
@RequiredArgsConstructor
public class MainController {
    private final ApplicationContext applicationContext;
    private final TaskService taskService;
    private final CategoryService categoryService;

    @FXML
    private Button categoryButton;

    @FXML
    private Button taskButton;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private void handleCategoryClick() throws IOException {
        log.info("Category button clicked");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/categories.fxml"));
        loader.setControllerFactory(applicationContext::getBean);
        Parent categoriesView = loader.load();
        
        AnchorPane.setTopAnchor(categoriesView, 0.0);
        AnchorPane.setRightAnchor(categoriesView, 0.0);
        AnchorPane.setBottomAnchor(categoriesView, 0.0);
        AnchorPane.setLeftAnchor(categoriesView, 0.0);
        
        contentPane.getChildren().setAll(categoriesView);
    }

    @FXML
    private void handleTaskClick() throws IOException {
        log.info("Task button clicked");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tasks.fxml"));
        loader.setControllerFactory(applicationContext::getBean);
        Parent tasksView = loader.load();
        
        AnchorPane.setTopAnchor(tasksView, 0.0);
        AnchorPane.setRightAnchor(tasksView, 0.0);
        AnchorPane.setBottomAnchor(tasksView, 0.0);
        AnchorPane.setLeftAnchor(tasksView, 0.0);
        
        contentPane.getChildren().setAll(tasksView);
    }

    @FXML
    private void handleTaskCreation(){
        log.info("Creating new task");

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Create task");
        dialog.setHeaderText("Create new Task");

        dialog.getDialogPane().getButtonTypes()
            .addAll(ButtonType.OK, ButtonType.CANCEL);
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nameField = new TextField();

        TextField descriptionField = new TextField();

        DatePicker limitDatePicker = new DatePicker(LocalDate.now());

        ComboBox<String> statusComboBox = new ComboBox<>();
        statusComboBox.setItems(FXCollections.observableArrayList(
            Arrays.stream(EStatus.values())
                .map(EStatus::getStatus)
                .toList()
        ));

        ComboBox<String> categoryComboBox = new ComboBox<>();
        List<CategoryResponse> categories = categoryService.getAllCategories();
        categoryComboBox.setItems(FXCollections.observableArrayList(
            categories.stream()
                .map(cat -> cat.name())
                .toList()
        ));


        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Description:"), 0, 1);
        grid.add(descriptionField, 1, 1);
        grid.add(new Label("Limit Date:"), 0, 2);
        grid.add(limitDatePicker, 1, 2);
        grid.add(new Label("Status:"), 0, 3);
        grid.add(statusComboBox, 1, 3);
        grid.add(new Label("Category:"), 0, 4);
        grid.add(categoryComboBox, 1, 4);

        dialog.getDialogPane().setContent(grid);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            String selectedCategoryId = categories.stream()
                .filter(cat -> cat.name().equals(categoryComboBox.getValue()))
                .findFirst()
                .map(cat -> cat.id())
                .orElse(null);

            TaskRequest createTask = new TaskRequest(
                nameField.getText(),
                descriptionField.getText(),
                limitDatePicker.getValue(),
                statusComboBox.getValue(),
                selectedCategoryId
            );

            log.info("Creating task: {}", createTask);
            taskService.createTask(createTask);
            log.info("Task created");
        }
    }

    @FXML
    private void handleCategoryCreation(){
        log.info("Creating new category");
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Create category");
        dialog.setHeaderText("Create new Category");

        dialog.getDialogPane().getButtonTypes()
            .addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nameField = new TextField();

        grid.add(new Label("Nom:"), 0, 0);
        grid.add(nameField, 1, 0);


        dialog.getDialogPane().setContent(grid);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            String newName = nameField.getText();
            log.info("New Name: {}", newName);
            categoryService.createCategory(new CategoryRequest(newName));
            log.info("Category created");
        }
    }
}
