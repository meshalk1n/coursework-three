package com.example.controllers;

import com.example.models.Estate;
import com.example.models.InventoryCard;
import com.example.services.AuthenticatedUserService;
import com.example.services.EstateService;
import com.example.services.InventoryCardService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import java.time.LocalDateTime;
import java.util.List;

@Controller
@FxmlView("/com/example/fxml/estate-form.fxml")
public class EstateFormController {

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
    private TableView<Estate> tableView;

    @Autowired
    public EstateFormController(AuthenticatedUserService authenticatedUserService,
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

        // Заполнение ComboBox значениями
        ObservableList<String> conditions = FXCollections.observableArrayList("Новый", "Б/У", "Сломан");
        conditionComboBox.setItems(conditions);

        // Установка слушателя для выбора значения в ComboBox
        conditionComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                conditionComboBox.setValue(newValue);
            }
        });

        // ---

        // Заполнение ComboBox значениями
        ObservableList<String> categories = FXCollections.observableArrayList("Офисное оборудование",
                "Мебель", "Транспортные средства", "Производственное оборудование", "Недвижимость");
        categoryComboBox.setItems(categories);

        // Установка слушателя для выбора значения в ComboBox
        categoryComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                categoryComboBox.setValue(newValue);
            }
        });



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

    @FXML
    public void saveEstate() {
        Estate newEstate = new Estate();
        newEstate.setName(nameField.getText());
        newEstate.setCategory(categoryComboBox.getValue());
        newEstate.setCost(Integer.parseInt(costField.getText()));
        newEstate.setCondition(conditionComboBox.getValue());
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
    public void clear() {
        nameField.clear();
        categoryComboBox.setValue(null);
        costField.clear();
        conditionComboBox.setValue(null);
    }

    @FXML
    public void updateEstate() {
        Estate selectedEstate = tableView.getSelectionModel().getSelectedItem();
        if (selectedEstate != null) {
            // Получение данных из полей ввода
            selectedEstate.setName(nameField.getText());
            selectedEstate.setCategory(categoryComboBox.getValue());
            selectedEstate.setCost(Integer.parseInt(costField.getText()));
            selectedEstate.setCondition(conditionComboBox.getValue());

            // Вызов метода updateUser в сервисе с указанием текущего пользователя
            estateService.updateEstate(selectedEstate, authenticatedUserService.getActiveUser().getUsername());;

            // Обновление отображения таблицы
            updateTableView();
        } else {
            // Если ни один пользователь не выбран, можете вывести сообщение об ошибке или предпринять другие действия.
        }
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

    @FXML
    public void inventoryCard() {
        transferData();
    }

    private void transferData() {
        // Получение выбранного объекта Estate
        Estate selectedEstate = tableView.getSelectionModel().getSelectedItem();

        // Создание новой карточки инвентаря
        InventoryCard newInventoryCard = new InventoryCard();

        // Установка свойств новой карточки инвентаря
        newInventoryCard.setEstate(selectedEstate);
        newInventoryCard.setInventoryDate(LocalDateTime.now());

        // Сохранение новой карточки инвентаря в базе данных
        inventoryCardService.saveInventoryCard(newInventoryCard, authenticatedUserService.getActiveUser().getUsername());

        // Обновление таблицы
        updateTableView();
    }
}
