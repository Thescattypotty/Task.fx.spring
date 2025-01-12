package org.javaFxApp.taskManagement.Controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;

import java.util.Optional;

import org.javaFxApp.taskManagement.Annotation.FxController;
import org.javaFxApp.taskManagement.Payload.Request.CategoryRequest;
import org.javaFxApp.taskManagement.Payload.Response.CategoryResponse;
import org.javaFxApp.taskManagement.Service.CategoryService;

import javafx.beans.property.SimpleStringProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

@FxController
@RequiredArgsConstructor
@Log4j2
public class CategoryController {
    private final CategoryService categoryService;

    @FXML
    private TableView<CategoryResponse> categoriesList;
    
    @FXML
    private TableColumn<CategoryResponse, String> name;
    
    @FXML
    private TableColumn<CategoryResponse, HBox> edit;

    @FXML
    public void initialize() {
        log.info("Category controller initialized");
        
        name.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().name()));
            
        edit.setCellFactory(column -> {
            return new TableCell<CategoryResponse, HBox>() {
                private final Button editButton = new Button("-");
                private final Button deleteButton = new Button("X");
                private final HBox buttons = new HBox(20, editButton, deleteButton);
                
                {
                    buttons.setAlignment(Pos.CENTER);
                    editButton.setOnAction(event -> handleEdit(getTableView().getItems().get(getIndex())));
                    deleteButton.setOnAction(event -> handleDelete(getTableView().getItems().get(getIndex())));
                }
                
                @Override
                protected void updateItem(HBox item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(buttons);
                    }
                }
            };
        });
            
        loadCategories();
    }

    private void loadCategories() {
        categoriesList.setItems(
            FXCollections.observableArrayList(categoryService.getAllCategories())
        );
        log.info("Categories loaded");
    }

    @FXML
    private void handleEdit(CategoryResponse category) {
        log.info("Editing category: {}", category.name());
        
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit category");
        dialog.setHeaderText("Edit Category : " + category.name());

        dialog.getDialogPane().getButtonTypes()
            .addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nameField = new TextField();
        nameField.setText(category.name());

        grid.add(new Label("Nom:"), 0, 0);
        grid.add(nameField, 1, 0);


        dialog.getDialogPane().setContent(grid);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            String newName = nameField.getText();
            log.info("New Name: {}", newName);
            categoryService.updateCategory(category.id(), new CategoryRequest(newName));
            log.info("Category updated");
            loadCategories();
        }
    }

    @FXML
    private void handleDelete(CategoryResponse category) {
        log.info("Deleting category: {}", category.name());
        categoryService.deleteCategory(category.id());
        loadCategories();
    }
}
