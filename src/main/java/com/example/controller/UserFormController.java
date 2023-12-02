package com.example.controller;

import com.example.entity.User;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@FxmlView("/com.example.controller/user-form.fxml")
public class UserFormController {

    private final LoginController loginController;
    @FXML
    public Button myProfileButton;

    @FXML
    private final MyProfileFormController myProfileFormController;


    @Autowired
    public UserFormController(LoginController loginController, MyProfileFormController myProfileFormController){
        this.loginController = loginController;
        this.myProfileFormController = myProfileFormController;
    }

    @FXML
    public void myProfile() {
        openMyProfileForm();
    }

    private void openMyProfileForm() {

        // Устанавливаем данные пользователя
        User loggedInUser = loginController.getCurrentLoggedInUser();

        // Проверяем, что myProfileFormController проинициализирован
        if (myProfileFormController != null) {
            myProfileFormController.setUserProfile(loggedInUser);

            // Загружаем и показываем форму "Мой профиль"
            Scene scene = new Scene(myProfileFormController);
            Stage stage = (Stage) myProfileButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else {
            // Обработка ситуации, когда myProfileFormController не проинициализирован
            System.out.println("MyProfileFormController is not initialized");
        }
    }
}
