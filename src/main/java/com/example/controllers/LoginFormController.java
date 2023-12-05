package com.example.controllers;

import com.example.models.User;
import com.example.services.AuthenticatedUserService;
import com.example.services.UserService;
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
import org.springframework.stereotype.Controller;


@Controller
@FxmlView("/com/example/fxml/login-form.fxml")
public class LoginFormController {

    @FXML
    public Label errorMessageLabel;

    @FXML
    public TextField usernameField;

    @FXML
    public PasswordField passwordField;

    private final UserService userService;

    private final FxWeaver fxWeaver;

    private final AuthenticatedUserService authenticatedUserService;

    @FXML
    private User loggedInUser;

    @Autowired
    public LoginFormController(UserService userService, FxWeaver fxWeaver, AuthenticatedUserService authenticatedUserService) {
        this.userService = userService;
        this.fxWeaver = fxWeaver;
        this.authenticatedUserService = authenticatedUserService;
    }

    @FXML
    private void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        this.loggedInUser = userService.getUserByUsernameAndPassword(username, password);
        if (loggedInUser != null) {
            authenticatedUserService.setActiveUser(loggedInUser);

            // Проверьте роли и откройте соответствующую форму
            if  (userService.getUsersByRoleContains("ROLE_ADMIN").contains(loggedInUser))  {
                openMainForm();
                System.out.println("АДМИН");
            } else {
                openMainForm();
                System.out.println("ПОЛЬЗОВАТЕЛЬ");
            }
        } else {
            errorMessageLabel.setText("Неверные учетные данные!");
        }
    }

    @FXML
    public User getCurrentLoggedInUser() {
        return loggedInUser;
    }

    private void openMainForm() {
        Parent root = fxWeaver.loadView(MainFormController.class);
        Scene scene = new Scene(root);
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void register() {
        openRegisterForm();
    }

    private void openRegisterForm(){
        Parent root = fxWeaver.loadView(RegisterFormController.class);
        Scene scene = new Scene(root);
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
