package com.example.controllers;

import com.example.models.User;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@FxmlView("/com/example/fxml/setting-form.fxml")
public class SettingFormController {

    @FXML
    public Button myProfileButton;

    private final LoginFormController loginFormController;

    private final FxWeaver fxWeaver;

    @FXML
    public Button backButton;

    @Autowired
    public SettingFormController(LoginFormController loginFormController, FxWeaver fxWeaver){
        this.loginFormController = loginFormController;
        this.fxWeaver = fxWeaver;
    }

    @FXML
    public void myProfile() {
        openMyProfileForm();
    }

    private void openMyProfileForm() {

        // Устанавливаем данные пользователя
        User loggedInUser = loginFormController.getCurrentLoggedInUser();

        if (loggedInUser != null) {
            MyProfileFormController myProfileFormController = fxWeaver.loadController(MyProfileFormController.class);
            myProfileFormController.setUserProfile(loggedInUser);

            // Загружаем и показываем форму "Мой профиль"
            Parent view = fxWeaver.loadView(MyProfileFormController.class);
            Scene scene = new Scene(view);
            Stage stage = (Stage) myProfileButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else {
            // Обработка ситуации, когда myProfileFormController не проинициализирован
            System.out.println("User or MyProfileFormController is null");
        }
    }

    @FXML
    public void back() {
        openMainForm();
    }

    private void openMainForm(){
        Parent view = fxWeaver.loadView(MainFormController.class);
        Scene scene = new Scene(view);
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
