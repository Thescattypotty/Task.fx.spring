package org.javaFxApp.javafxApp.Controller;

import org.javaFxApp.javafxApp.Annotation.FxController;
import org.javaFxApp.javafxApp.Payload.Response.CategoryResponse;
import org.javaFxApp.javafxApp.Service.CategoryService;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.beans.property.SimpleStringProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;
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

    private void handleEdit(CategoryResponse category) {
        log.info("Editing category: {}", category.name());
    }

    private void handleDelete(CategoryResponse category) {
        log.info("Deleting category: {}", category.name());
        categoryService.deleteCategory(category.id());
        loadCategories();
    }
}
