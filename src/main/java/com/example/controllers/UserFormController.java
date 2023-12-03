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
@FxmlView("/com/example/fxml/user-form.fxml")
public class UserFormController {

    private final LoginFormController loginFormController;
    @FXML
    public Button myProfileButton;

    private final FxWeaver fxWeaver;


    @Autowired
    public UserFormController(LoginFormController loginFormController, FxWeaver fxWeaver){
        this.loginFormController = loginFormController;
        this.fxWeaver = fxWeaver;
    }

    @FXML
    public void myProfile() {
        openMyProfileForm();
    }

    /*
    TODO: Здесь нужно использовать такой переход, проблема связана с тем,
     как JavaFX и Spring взаимодействуют друг с другом.
     FxWeaver - это библиотека, которая позволяет Spring и JavaFX работать вместе более гладко.
     */
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
}
