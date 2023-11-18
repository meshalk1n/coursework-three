package com.example.controller;

import com.example.service.UserService;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@FxmlView("/com.example.controller/login.fxml")
public class LoginController {

    @FXML
    public Label errorMessageLabel;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private final UserService userService;

    private final FxWeaver fxWeaver;

    @Autowired
    public LoginController(UserService userService, FxWeaver fxWeaver) {
        this.userService = userService;
        this.fxWeaver = fxWeaver;
    }

    @FXML
    private void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (isValidCredentials(username, password)) {
            openSecondMainForm();
        } else {
            errorMessageLabel.setText("Invalid credentials!");
        }
    }

    private void openSecondMainForm(){
        Parent root = fxWeaver.loadView(MainController.class);
        Scene scene = new Scene(root);
        Stage stage = (Stage) usernameField.getScene().getWindow(); // Получаем текущий Stage
        stage.setScene(scene);
        stage.show();
    }

    private boolean isValidCredentials(String username, String password) {
        return userService.getUserByUsernameAndPassword(username, password) != null;
    }

    @FXML
    private void register() {
        openRegisterForm();
    }

    private void openRegisterForm(){
        Parent root = fxWeaver.loadView(RegisterController.class);
        Scene scene = new Scene(root);
        Stage stage = (Stage) usernameField.getScene().getWindow(); // Получаем текущий Stage
        stage.setScene(scene);
        stage.show();
    }
}