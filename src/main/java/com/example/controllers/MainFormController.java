package com.example.controllers;

import com.example.models.User;
import com.example.services.AuthenticatedUserService;
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
@FxmlView("/com/example/fxml/main-form.fxml")
public class MainFormController {

    private final FxWeaver fxWeaver;

    @FXML
    public Button settingButton;

    @FXML
    public Button adminFormButton;

    private final AuthenticatedUserService authenticatedUserService;


    @Autowired
    public MainFormController(FxWeaver fxWeaver, AuthenticatedUserService authenticatedUserService){
        this.fxWeaver = fxWeaver;
        this.authenticatedUserService = authenticatedUserService;
    }

    @FXML
    public void initialize() {
        // Используйте метод из AuthenticatedUserService, чтобы получить текущего пользователя
        User currentUser = authenticatedUserService.getActiveUser();

        // Проверьте роли и скройте/покажите кнопку
        adminFormButton.setVisible(currentUser != null && currentUser.getRole().contains("ROLE_ADMIN"));
    }

    @FXML
    public void setting() {
        openSettingForm();
    }

    private void openSettingForm(){
        Parent view = fxWeaver.loadView(SettingFormController.class);
        Scene scene = new Scene(view);
        Stage stage = (Stage) settingButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void admin() {
        openAdminForm();
    }

    private void openAdminForm(){
        Parent view = fxWeaver.loadView(AdminFormController.class);
        Scene scene = new Scene(view);
        Stage stage = (Stage) adminFormButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
