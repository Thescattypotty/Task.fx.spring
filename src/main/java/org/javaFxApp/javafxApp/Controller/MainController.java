package org.javaFxApp.javafxApp.Controller;

import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

@Component
public class MainController {
    
    @FXML
    private Label welcomeLabel;

    @FXML
    public void handleButtonClick(){
        welcomeLabel.setText("Welcome to JavaFX Application!");
    }
}
