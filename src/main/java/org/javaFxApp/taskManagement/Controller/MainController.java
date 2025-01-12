package org.javaFxApp.taskManagement.Controller;

import java.io.IOException;

import org.javaFxApp.taskManagement.Annotation.FxController;
import org.springframework.context.ApplicationContext;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import lombok.extern.log4j.Log4j2;


@FxController
@Log4j2
public class MainController {
    private final ApplicationContext applicationContext;

    public MainController(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
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
        
        // Make the loaded view fill the available space
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
        
        // Make the loaded view fill the available space
        AnchorPane.setTopAnchor(tasksView, 0.0);
        AnchorPane.setRightAnchor(tasksView, 0.0);
        AnchorPane.setBottomAnchor(tasksView, 0.0);
        AnchorPane.setLeftAnchor(tasksView, 0.0);
        
        contentPane.getChildren().setAll(tasksView);
    }
}
