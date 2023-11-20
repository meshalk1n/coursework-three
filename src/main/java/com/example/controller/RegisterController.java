package com.example.controller;

import com.example.entity.User;
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
@FxmlView("/com.example.controller/register.fxml")
public class RegisterController {

    @FXML
    public Label errorMessageLabel;

    @FXML
    public TextField roleField;

    @FXML
    public TextField emailField;

    @FXML
    public TextField usernameField;

    @FXML
    public PasswordField passwordField;

    private final FxWeaver fxWeaver;

    private final UserService userService;

    @Autowired
    public RegisterController(FxWeaver fxWeaver, UserService userService) {
        this.fxWeaver = fxWeaver;
        this.userService = userService;
    }

    @FXML
    public void register() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String role = roleField.getText();
        String email = emailField.getText();

        if (checkExistence(username, email)){
            errorMessageLabel.setText("Error");
        } else {
            User newUser = new User(username, password, role, email);
            userService.saveUser(newUser);
            openLoginForm();
        }
    }

    private boolean checkExistence(String username, String email){
        return userService.getUserByUsername(username) != null || userService.getUserByEmail(email) != null;
    }

    private void openLoginForm(){
        Parent root = fxWeaver.loadView(LoginController.class);
        Scene scene = new Scene(root);
        Stage stage = (Stage) usernameField.getScene().getWindow(); // Получаем текущий Stage
        stage.setScene(scene);
        stage.show();
    }
}
