package com.example.controllers;

import com.example.models.Estate;
import com.example.models.User;
import com.example.services.AuthenticatedUserService;
import com.example.services.EstateService;
import com.example.services.InventoryCardService;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@FxmlView("/com/example/fxml/list-estate-form.fxml")
public class ListEstateFormController {

    private final EstateService estateService;

    private final AuthenticatedUserService authenticatedUserService;

    private final FxWeaver fxWeaver;

    private final InventoryCardService inventoryCardService;

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
    public TextField costField;

    @FXML
    public ComboBox<String> conditionComboBox;

    @FXML
    public TableColumn lastModifiedByColumn;

    @FXML
    public TextField searchField;

    @FXML
    public Button clearButton;

    @FXML
    public ComboBox<String> categoryComboBox;

    @FXML
    public Button saveEstateButton;

    @FXML
    public Button deleteEstateButton;

    @FXML
    public Button updateEstateButton;

    @FXML
    public Button inventoryCardButton;

    @FXML
    private TableView<Estate> tableView;

    @Autowired
    public ListEstateFormController(AuthenticatedUserService authenticatedUserService,
                                FxWeaver fxWeaver, EstateService estateService,
                                InventoryCardService inventoryCardService) {
        this.estateService = estateService;
        this.authenticatedUserService = authenticatedUserService;
        this.fxWeaver = fxWeaver;
        this.inventoryCardService = inventoryCardService;
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

        // Получение текущего пользователя
        User currentUser = authenticatedUserService.getActiveUser();

        // Проверка роли пользователя
        if ((!currentUser.getRole().equals("ROLE_ADMIN")) && (!currentUser.getRole().equals("ROLE_ASSET_MANAGER"))) {
            // Если пользователь не является администратором, скрыть элементы
            nameField.setVisible(false);
            categoryComboBox.setVisible(false);
            costField.setVisible(false);
            conditionComboBox.setVisible(false);
            saveEstateButton.setVisible(false);
            deleteEstateButton.setVisible(false);
            updateEstateButton.setVisible(false);
            clearButton.setVisible(false);
        }if((!currentUser.getRole().equals("ROLE_ADMIN")) && (!currentUser.getRole().equals("ROLE_INVENTORY_OFFICER"))){
            inventoryCardButton.setVisible(false);
        }


        // Загрузка пользователей при инициализации
        updateTableView();

        // Добавление слушателя для выбора строки в таблице
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Заполнение полей данными выбранного пользователя
                nameField.setText(newValue.getName());
                categoryComboBox.setValue(newValue.getCategory());
                costField.setText(String.valueOf(newValue.getCost()));
                conditionComboBox.setValue(newValue.getCondition());
            }
        });

        // Добавление слушателя изменений текста в поле поиска
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchEstate(newValue);
        });

        // Запуск начального поиска с пустым значением (отобразить все пользователи)
        searchEstate("");
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

    private void searchEstate(String searchTerm) {
        if (!searchTerm.isEmpty()) {
            List<Estate> foundEstate = estateService.getEstateByNameContains(searchTerm);

            if (!foundEstate.isEmpty()) {
                // Отображение найденных пользователей в таблице
                tableView.getItems().setAll(foundEstate);
            } else {
                // Можете добавить обработку, если пользователь не найден
                // Например, вы можете вывести сообщение об отсутствии результатов.
            }
        } else {
            // Если поле поиска пустое, отобразите все пользователи
            updateTableView();
        }
    }
}
