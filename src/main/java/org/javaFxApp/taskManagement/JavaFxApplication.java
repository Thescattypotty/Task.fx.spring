package org.javaFxApp.taskManagement;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

@SpringBootApplication
@EnableJpaAuditing
public class JavaFxApplication extends Application{

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() throws Exception{
        applicationContext = new SpringApplicationBuilder(JavaFxApplication.class).run();
    }

	public static void main(String[] args) {
		launch(args);
	}

    @Override
    public void start(Stage arg0) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        fxmlLoader.setControllerFactory(applicationContext::getBean);

        Parent root = fxmlLoader.load();
        arg0.setScene(new Scene(root));
        arg0.show();
    }

    @Override
    public void stop() throws Exception{
        applicationContext.close();
        //System.exit(0);
    }
}
