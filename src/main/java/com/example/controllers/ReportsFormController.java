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
@FxmlView("/com/example/fxml/reports-form.fxml")
public class ReportsFormController {
    
    @FXML
    public Button backButton;

    private final FxWeaver fxWeaver;

    @Autowired
    public ReportsFormController(FxWeaver fxWeaver) {
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
}