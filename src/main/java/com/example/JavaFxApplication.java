package com.example;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import javafx.scene.control.Label;

public class JavaFxApplication extends javafx.application.Application {
    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() {
        applicationContext = new SpringApplicationBuilder(Application.class).run();
    }

    @Override
    public void start(Stage stage) {
        Label label = new Label("Hello, JavaFX with Spring Boot!");
        Scene scene = new Scene(label, 200, 100);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }
}
