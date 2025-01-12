package org.javaFxApp.javafxApp.Controller;

import org.javaFxApp.javafxApp.Annotation.FxController;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

@FxController
public class MainController {
    
    @FXML
    private Label welcomeLabel;

    @FXML
    public void handleButtonClick(){
        welcomeLabel.setText("Welcome to JavaFX Application!");
    }
}
