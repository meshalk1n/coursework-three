package com.example.controllers;

import com.example.models.Estate;
import com.example.models.InventoryCard;
import com.example.services.AuthenticatedUserService;
import com.example.services.InventoryCardService;
import javafx.event.ActionEvent;
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
@FxmlView("/com/example/fxml/inventory-card-form.fxml")
public class InventoryCardFormController {

    private final FxWeaver fxWeaver;

    private final InventoryCardService inventoryCardService;

    private final AuthenticatedUserService authenticatedUserService;

    @FXML
    public TextField searchField;

    @FXML
    private TableView<InventoryCard> tableView;

    @FXML
    public Button backButton;

    @FXML
    public TableColumn idColumn;

    @FXML
    public TableColumn inventoryDateColumn;

    @FXML
    public TableColumn inventoryOfficerColumn;

    @FXML
    public TableColumn notesColumn;

    @FXML
    public TableColumn locationColumn;

    @FXML
    public TableColumn statusColumn;

    @FXML
    public Button updateInventoryCordButton;

    @FXML
    public TextField notesField;

    @FXML
    public TextField locationField;

    @FXML
    public TextField statusField;

    @FXML
    public Button clearButton;

    @Autowired
    public InventoryCardFormController(FxWeaver fxWeaver, InventoryCardService inventoryCardService,
                                       AuthenticatedUserService authenticatedUserService) {
        this.fxWeaver = fxWeaver;
        this.inventoryCardService = inventoryCardService;
        this.authenticatedUserService = authenticatedUserService;
    }

    //проверка
    @FXML
    public void initialize() {
        // Связывание столбцов таблицы с полями объектов
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        inventoryDateColumn.setCellValueFactory(new PropertyValueFactory<>("inventoryDate"));
        inventoryOfficerColumn.setCellValueFactory(new PropertyValueFactory<>("inventoryOfficer"));
        notesColumn.setCellValueFactory(new PropertyValueFactory<>("notes"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Загрузка пользователей при инициализации
        updateTableView();

        // Добавление слушателя для выбора строки в таблице
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Заполнение полей данными выбранного пользователя
                notesField.setText(newValue.getNotes());
                locationField.setText(newValue.getLocation());
                statusField.setText(newValue.getStatus());
            }
        });

        // Добавление слушателя изменений текста в поле поиска
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchInventoryCord(newValue);
        });

        // Запуск начального поиска с пустым значением (отобразить все пользователи)
        searchInventoryCord("");
    }

    private void updateTableView() {
        List<InventoryCard> inventoryCard = inventoryCardService.getAllInventoryCard();
        tableView.getItems().setAll(inventoryCard);
    }

    @FXML
    public void back() {
        openAssetsFor();
    }

    private void openAssetsFor(){
        Parent view = fxWeaver.loadView(AssetsFormController.class);
        Scene scene = new Scene(view);
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private void searchInventoryCord(String searchTerm) {
        if (!searchTerm.isEmpty()) {
            List<InventoryCard> foundInventoryCord = inventoryCardService.getEstateByStatusContains(searchTerm);

            if (!foundInventoryCord.isEmpty()) {
                // Отображение найденных пользователей в таблице
                tableView.getItems().setAll(foundInventoryCord);
            } else {
                // Можете добавить обработку, если пользователь не найден
                // Например, вы можете вывести сообщение об отсутствии результатов.
            }
        } else {
            // Если поле поиска пустое, отобразите все пользователи
            updateTableView();
        }
    }

    public void updateInventoryCord() {
        InventoryCard selectedInventoryCard = tableView.getSelectionModel().getSelectedItem();
        if (selectedInventoryCard != null) {
            // Получение данных из полей ввода
            selectedInventoryCard.setNotes(notesField.getText());
            selectedInventoryCard.setLocation(locationField.getText());
            selectedInventoryCard.setStatus(statusField.getText());

            // Вызов метода updateUser в сервисе с указанием текущего пользователя
            inventoryCardService.updateInventoryCard(selectedInventoryCard,
                    authenticatedUserService.getActiveUser().getUsername());;

            // Обновление отображения таблицы
            updateTableView();
        } else {
            // Если ни один пользователь не выбран, можете вывести сообщение об ошибке или предпринять другие действия.
        }
    }

    @FXML
    public void deleteInventoryCord() {
        InventoryCard selectedInventoryCard = tableView.getSelectionModel().getSelectedItem();
        if (selectedInventoryCard != null) {
            inventoryCardService.deleteInventoryCard(selectedInventoryCard);
            // Обновление отображения таблицы
            updateTableView();
        }
    }

    @FXML
    public void clear() {
        notesField.clear();
        locationField.clear();
        statusField.clear();
    }
}
