package com.example.controllers;

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
@FxmlView("/com/example/fxml/assets-form.fxml")
public class AssetsFormController {

    @FXML
    public Button backButton;

    private final FxWeaver fxWeaver;

    @FXML
    public Button estateButton;

    @Autowired
    public AssetsFormController(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
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

    public void estate() {
        openEstateForm();
    }

    private void openEstateForm(){
        Parent view = fxWeaver.loadView(EstateFormController.class);
        Scene scene = new Scene(view);
        Stage stage = (Stage) estateButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
