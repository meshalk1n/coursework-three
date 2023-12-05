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

    @FXML
    public Button viewAssetsButton;

    @FXML
    public Button reportsButton;

    @FXML
    public Button backButton;

    private final LoginFormController loginFormController;

    @Autowired
    public MainFormController(FxWeaver fxWeaver, AuthenticatedUserService authenticatedUserService,
                              LoginFormController loginFormController){
        this.fxWeaver = fxWeaver;
        this.authenticatedUserService = authenticatedUserService;
        this.loginFormController = loginFormController;
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

    @FXML
    public void viewAssets() {
        openAssetsForm();
    }

    private void openAssetsForm(){
        Parent view = fxWeaver.loadView(AssetsFormController.class);
        Scene scene = new Scene(view);
        Stage stage = (Stage) viewAssetsButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void reports() {
        openReportsForm();
    }

    private void openReportsForm(){
        Parent view = fxWeaver.loadView(ReportsFormController.class);
        Scene scene = new Scene(view);
        Stage stage = (Stage) reportsButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void back() {
        loginFormController.resetUserData();
        openLoginForm();
    }

    private void openLoginForm(){
        Parent view = fxWeaver.loadView(LoginFormController.class);
        Scene scene = new Scene(view);
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
