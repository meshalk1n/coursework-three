package com.example.controllers;

import com.example.models.User;
import com.example.services.AuthenticatedUserService;
import com.example.services.UserService;
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

import java.util.List;
import java.util.Optional;

@Controller
@FxmlView("/com/example/fxml/admin-form.fxml")
public class AdminFormController {

    @FXML
    public TableColumn usernameColumn;

    @FXML
    public TableColumn passwordColumn;

    @FXML
    public TableColumn idColumn;

    @FXML
    public TableColumn roleColumn;

    @FXML
    public TableColumn emailColumn;

    @FXML
    public TableColumn creationDateColumn;

    @FXML
    public TextField emailField;

    @FXML
    public TableColumn lastModifiedByColumn;

    @FXML
    public Button backButton;

    @FXML
    public Button clearButton;

    @FXML
    public TextField searchField;

    @FXML
    public TableColumn addedByUserColumn;

    @FXML
    public ComboBox<String> roleComboBox;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TableView<User> tableView;

    private final UserService userService;

    private final AuthenticatedUserService authenticatedUserService;

    private final FxWeaver fxWeaver;

    @Autowired
    public AdminFormController(UserService userService, AuthenticatedUserService authenticatedUserService,
                               FxWeaver fxWeaver) {
        this.userService = userService;
        this.authenticatedUserService = authenticatedUserService;
        this.fxWeaver = fxWeaver;
    }
    @FXML
    public void initialize() {
        // Связывание столбцов таблицы с полями объектов User
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        creationDateColumn.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        addedByUserColumn.setCellValueFactory(new PropertyValueFactory<>("addedByUser"));
        lastModifiedByColumn.setCellValueFactory(new PropertyValueFactory<>("lastModifiedBy"));

        // Загрузка пользователей при инициализации
        updateTableView();

        // Добавление слушателя для выбора строки в таблице
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Заполнение полей данными выбранного пользователя
                usernameField.setText(newValue.getUsername());
                passwordField.setText(newValue.getPassword());
                roleComboBox.setValue(newValue.getRole());
                emailField.setText(newValue.getEmail());

            }
        });

        // Заполнение ComboBox значениями
        ObservableList<String> conditions = FXCollections.observableArrayList("ROLE_ADMIN","ROLE_ASSET_MANAGER" ,
                "ROLE_INVENTORY_OFFICER", "ROLE_USER");
        roleComboBox.setItems(conditions);

        // Установка слушателя для выбора значения в ComboBox
        roleComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                roleComboBox.setValue(newValue);
            }
        });

        // Добавление слушателя изменений текста в поле поиска
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchUser(newValue);
        });

        // Запуск начального поиска с пустым значением (отобразить все пользователи)
        searchUser("");
    }

    @FXML
    public void saveUser() {
        User newUser = new User();
        newUser.setUsername(usernameField.getText());
        newUser.setPassword(passwordField.getText());
        newUser.setRole(roleComboBox.getValue());
        newUser.setEmail(emailField.getText());
        userService.saveUser(newUser, authenticatedUserService.getActiveUser().getUsername());
        // Обновление отображения таблицы
        updateTableView();
    }

    @FXML
    public void deleteUser() {
        User selectedUser = tableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            // Создание нового диалогового окна с подтверждением
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Подтверждение удаления");
            alert.setHeaderText(null);
            alert.setContentText("Вы точно хотите удалить этот объект?");

            // Отображение диалогового окна и ожидание решения пользователя
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Если пользователь подтвердил удаление, удалить объект
                userService.deleteUser(selectedUser);
                // Обновление отображения таблицы
                updateTableView();
            }
        }
    }

    @FXML
    public void updateUser() {
        User selectedUser = tableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            // Получение данных из полей ввода
            selectedUser.setUsername(usernameField.getText());
            selectedUser.setPassword(passwordField.getText());
            selectedUser.setRole(roleComboBox.getValue());
            selectedUser.setEmail(emailField.getText());

            // Вызов метода updateUser в сервисе с указанием текущего пользователя
            userService.updateUser(selectedUser, authenticatedUserService.getActiveUser().getUsername());;

            // Обновление отображения таблицы
            updateTableView();
        } else {
            // Если ни один пользователь не выбран, можете вывести сообщение об ошибке или предпринять другие действия.
        }
    }

    private void updateTableView() {
        List<User> users = userService.getAllUsers();
        tableView.getItems().setAll(users);
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

    @FXML
    public void clear() {
        usernameField.clear();
        passwordField.clear();
        roleComboBox.setValue(null);
        emailField.clear();
    }

    private void searchUser(String searchTerm) {
        if (!searchTerm.isEmpty()) {
            List<User> foundUsers = userService.getUsersByUsernameContains(searchTerm);

            if (!foundUsers.isEmpty()) {
                // Отображение найденных пользователей в таблице
                tableView.getItems().setAll(foundUsers);
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
