package com.example.controllers;

import com.example.models.Estate;
import com.example.models.User;
import com.example.services.AuthenticatedUserService;
import com.example.services.EstateService;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@FxmlView("/com/example/fxml/estate-form.fxml")
public class EstateFormController {

    private final EstateService estateService;

    private final AuthenticatedUserService authenticatedUserService;

    private final FxWeaver fxWeaver;

    @FXML
    public TableColumn idColumn;

    @FXML
    public TableColumn nameColumn;

    @FXML
    public TableColumn categoryColumn;

    @FXML
    public TableColumn costColumn;

    @FXML
    public TableColumn conditionColumn;

    @FXML
    public TableColumn acquisitionDateColumn;

    @FXML
    public TableColumn addedByUserColumn;

    @FXML
    public Button backButton;

    @FXML
    public TextField nameField;

    @FXML
    public TextField categoryField;

    @FXML
    public TextField costField;

    @FXML
    public TextField conditionField;

    @FXML
    public TableColumn lastModifiedByColumn;

    @FXML
    private TableView<Estate> tableView;

    @Autowired
    public EstateFormController(AuthenticatedUserService authenticatedUserService,
                                FxWeaver fxWeaver, EstateService estateService) {
        this.estateService = estateService;
        this.authenticatedUserService = authenticatedUserService;
        this.fxWeaver = fxWeaver;
    }

    @FXML
    public void initialize() {
        // Связывание столбцов таблицы с полями объектов Estate
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));
        conditionColumn.setCellValueFactory(new PropertyValueFactory<>("condition"));
        acquisitionDateColumn.setCellValueFactory(new PropertyValueFactory<>("acquisitionDate"));
        addedByUserColumn.setCellValueFactory(new PropertyValueFactory<>("addedByUser"));
        lastModifiedByColumn.setCellValueFactory(new PropertyValueFactory<>("lastModifiedBy"));

        // Загрузка пользователей при инициализации
        updateTableView();
    }

    private void updateTableView() {
        List<Estate> estate = estateService.getAllEstate();
        tableView.getItems().setAll(estate);
    }

    @FXML
    public void back(){
        openAssetsFor();
    }

    private void openAssetsFor(){
        Parent view = fxWeaver.loadView(AssetsFormController.class);
        Scene scene = new Scene(view);
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void saveEstate() {
        Estate newEstate = new Estate();
        newEstate.setName(nameField.getText());
        newEstate.setCategory(categoryField.getText());
        newEstate.setCost(Integer.parseInt(costField.getText()));
        newEstate.setCondition(conditionField.getText());
        estateService.saveEstate(newEstate, authenticatedUserService.getActiveUser().getUsername());

        // Обновление отображения таблицы
        updateTableView();
    }

    @FXML
    public void deleteEstate() {
        Estate selectedEstate = tableView.getSelectionModel().getSelectedItem();
        if (selectedEstate != null) {
            estateService.deleteEstate(selectedEstate);
            // Обновление отображения таблицы
            updateTableView();
        }
    }

    @FXML
    public void updateEstate() {
        Estate selectedEstate = tableView.getSelectionModel().getSelectedItem();
        if (selectedEstate != null) {
            // Получение данных из полей ввода
            selectedEstate.setName(nameField.getText());
            selectedEstate.setCategory(categoryField.getText());
            selectedEstate.setCost(Integer.parseInt(costField.getText()));
            selectedEstate.setCondition(conditionField.getText());

            // Вызов метода updateUser в сервисе с указанием текущего пользователя
            estateService.updateEstate(selectedEstate, authenticatedUserService.getActiveUser().getUsername());;

            // Обновление отображения таблицы
            updateTableView();
        } else {
            // Если ни один пользователь не выбран, можете вывести сообщение об ошибке или предпринять другие действия.
        }
    }
}
