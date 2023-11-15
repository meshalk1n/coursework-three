package com.example;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.example.repositories")
@EntityScan("com.example.entity")
public class SpringBootExampleApplication {

    public static void main(String[] args) {
        Application.launch(JavaFxApplication.class, args);
    }
}
