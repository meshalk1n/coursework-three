package com.example.controller;

import com.example.entity.User;
import com.example.service.CurrentUserService;
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
    public TextField usernameField;

    @FXML
    public PasswordField passwordField;

    private final UserService userService;

    private final FxWeaver fxWeaver;

    private final CurrentUserService currentUserService;

    private User loggedInUser;

    @Autowired
    public LoginController(UserService userService, FxWeaver fxWeaver, CurrentUserService currentUserService) {
        this.userService = userService;
        this.fxWeaver = fxWeaver;
        this.currentUserService = currentUserService;
    }

    @FXML
    private void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        this.loggedInUser = userService.getUserByUsernameAndPassword(username, password);
        if (loggedInUser != null) {
            currentUserService.setCurrentUser(loggedInUser);

            // Проверьте роли и откройте соответствующую форму
            if  (userService.getUsersByRoleContains("ROLE_ADMIN").contains(loggedInUser))  {
                openAdminForm();
                System.out.println("АДМИН");
            } else {
                openUserForm();
                System.out.println("ПОЛЬЗОВАТЕЛЬ");
            }
        } else {
            errorMessageLabel.setText("Неверные учетные данные!");
        }
    }

    public User getCurrentLoggedInUser() {
        return loggedInUser;
    }

    private void openAdminForm() {
        Parent root = fxWeaver.loadView(AdminFormController.class);
        Scene scene = new Scene(root);
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private void openUserForm() {
        Parent root = fxWeaver.loadView(UserFormController.class);
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
        Parent root = fxWeaver.loadView(RegisterController.class);
        Scene scene = new Scene(root);
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
