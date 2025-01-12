package org.javaFxApp.taskManagement.Controller;

import java.util.List;

import org.javaFxApp.taskManagement.Annotation.FxController;
import org.javaFxApp.taskManagement.Payload.Response.TaskResponse;
import org.javaFxApp.taskManagement.Service.TaskService;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import javafx.scene.control.Button;

@FxController
@RequiredArgsConstructor
@Log4j2
public class TaskController {
    private final TaskService taskService;

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
    private TableColumn<TaskResponse, String> createdAt;
    
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
            new SimpleStringProperty(cellData.getValue().categoryId())
        );
        createdAt.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().createdAt().toString())
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
    private void handleEdit(TaskResponse task){
        log.info("Edit task: {}", task);
    }
    private void handleDelete(TaskResponse task){
        log.info("Delete task: {}", task);
        taskService.deleteTask(task.id());
        loadTasks();
    }
}
