package com.example.controller;

import com.example.entity.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("/com.example.controller/my-profile-form.fxml")
public class MyProfileFormController extends Parent {


    @FXML
    private Label usernameLabel;
    @FXML
    private Label roleLabel;
    @FXML
    private Label emailLabel;

    @FXML
    public void initialize() {

    }

    @FXML
    public void setUserProfile(User user) {
        Platform.runLater(() -> {
            usernameLabel.setText(user.getUsername());
            roleLabel.setText(user.getRole());
            emailLabel.setText(user.getEmail());
        });
    }
}
