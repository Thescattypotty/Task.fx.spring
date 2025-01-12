package org.javaFxApp.taskManagement.Controller;

import java.util.List;

import org.javaFxApp.taskManagement.Annotation.FxController;
import org.javaFxApp.taskManagement.Payload.Response.CategoryResponse;
import org.javaFxApp.taskManagement.Payload.Response.TaskResponse;
import org.javaFxApp.taskManagement.Service.CategoryService;
import org.javaFxApp.taskManagement.Service.TaskService;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import org.javaFxApp.taskManagement.Enum.EStatus;
import org.javaFxApp.taskManagement.Payload.Request.TaskRequest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

@FxController
@RequiredArgsConstructor
@Log4j2
public class TaskController {
    private final TaskService taskService;
    private final CategoryService categoryService;

    @FXML
    private TableView<TaskResponse> tasksList;

    @FXML
    private TableColumn<TaskResponse, String> name;

    @FXML
    private TableColumn<TaskResponse, String> status;

    @FXML
    private TableColumn<TaskResponse, String> limitDate;

    @FXML
    private TableColumn<TaskResponse, String> category;

    @FXML
    private TableColumn<TaskResponse, String> updatedAt;
    
    @FXML
    private TableColumn<TaskResponse, HBox> edit;


    @FXML
    public void initialize() {
        log.info("Task controller initialized");
        name.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().name())
        );
        status.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().status())
        );
        limitDate.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().limitDate().toString())
        );
        category.setCellValueFactory(cellData -> 
            new SimpleStringProperty(
                categoryService.getCategory(cellData.getValue().categoryId()).name()
                )
        );
        updatedAt.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().updatedAt().toString())
        );
        edit.setCellFactory(column -> {
            return new TableCell<TaskResponse, HBox>(){
                private final Button editButton = new Button("-");
                private final Button deleteButton = new Button("X");
                private final HBox buttons = new HBox(20, editButton, deleteButton);

                {
                    buttons.setAlignment(Pos.CENTER);
                    editButton.setOnAction(event -> handleEdit(getTableView().getItems().get(getIndex())));
                    deleteButton.setOnAction(event -> handleDelete(getTableView().getItems().get(getIndex())));
                }

                @Override
                protected void updateItem(HBox item, boolean empty){
                    super.updateItem(item, empty);
                    if(empty){
                        setGraphic(null);
                    }else{
                        setGraphic(buttons);
                    }
                }
            };
        });
        loadTasks();
    }

    private void loadTasks(){
        List<TaskResponse> tasks = taskService.getTasks();
        if(tasks.isEmpty()){
            log.info("No tasks found");
            tasksList.setItems(FXCollections.observableArrayList());
        }else{
            log.info("Tasks loaded");
            tasksList.setItems(
                FXCollections.observableArrayList(taskService.getTasks())
            );
        }
        
    }

    @FXML
    private void handleEdit(TaskResponse task){
        log.info("Edit task: {}", task);

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit task");
        dialog.setHeaderText("Edit Task : " + task.name());

        dialog.getDialogPane().getButtonTypes()
            .addAll(ButtonType.OK, ButtonType.CANCEL);
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nameField = new TextField();
        nameField.setText(task.name());

        TextField descriptionField = new TextField();
        descriptionField.setText(task.description());

        DatePicker limitDatePicker = new DatePicker(LocalDate.parse(task.limitDate()));

        ComboBox<String> statusComboBox = new ComboBox<>();
        statusComboBox.setItems(FXCollections.observableArrayList(
            Arrays.stream(EStatus.values())
                .map(EStatus::getStatus)
                .toList()
        ));
        statusComboBox.setValue(task.status());

        ComboBox<String> categoryComboBox = new ComboBox<>();
        List<CategoryResponse> categories = categoryService.getAllCategories();
        categoryComboBox.setItems(FXCollections.observableArrayList(
            categories.stream()
                .map(cat -> cat.name())
                .toList()
        ));
        categoryComboBox.setValue(
            categories.stream()
                .filter(cat -> cat.id().equals(task.categoryId()))
                .findFirst()
                .map(cat -> cat.name())
                .orElse("")
        );

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
                .orElse(task.categoryId());

            TaskRequest updatedTask = new TaskRequest(
                nameField.getText(),
                descriptionField.getText(),
                limitDatePicker.getValue(),
                statusComboBox.getValue(),
                selectedCategoryId
            );

            log.info("Updating task with new values: {}", updatedTask);
            taskService.updateTask(task.id(), updatedTask);
            loadTasks();
        }
    }

    private void handleDelete(TaskResponse task){
        log.info("Delete task: {}", task);
        taskService.deleteTask(task.id());
        loadTasks();
    }
}
