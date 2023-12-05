package com.example.controllers;

import com.example.models.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@FxmlView("/com/example/fxml/my-profile-form.fxml")
public class MyProfileFormController extends Parent {

    @FXML
    public Button backButton;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label roleLabel;

    @FXML
    private Label emailLabel;

    private final FxWeaver fxWeaver;

    @Autowired
    public MyProfileFormController(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
    }

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

    @FXML
    public void back() {
        openSettingForm();
    }

    private void openSettingForm(){
        Parent view = fxWeaver.loadView(SettingFormController.class);
        Scene scene = new Scene(view);
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
