package com.example.controller;

import com.example.entity.User;
import com.example.service.CurrentUserService;
import com.example.service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@FxmlView("/com.example.controller/admin-form.fxml")
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
    public TextField roleField;

    @FXML
    public TextField emailField;

    @FXML
    public TableColumn lastModifiedByColumn;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TableView<User> tableView;

    private final UserService userService;

    private CurrentUserService currentUserService;

    @Autowired
    public AdminFormController(UserService userService, CurrentUserService currentUserService) {
        this.userService = userService;
        this.currentUserService = currentUserService;
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
        lastModifiedByColumn.setCellValueFactory(new PropertyValueFactory<>("lastModifiedBy"));

        // Загрузка пользователей при инициализации
        updateTableView();
    }

    @FXML
    public void saveUser() {
        User newUser = new User();
        newUser.setUsername(usernameField.getText());
        newUser.setPassword(passwordField.getText());
        newUser.setRole(roleField.getText());
        newUser.setEmail(emailField.getText());
        userService.saveUser(newUser);
        // Обновление отображения таблицы
        updateTableView();
    }

    @FXML
    public void deleteUser() {
        User selectedUser = tableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            userService.deleteUser(selectedUser);
            // Обновление отображения таблицы
            updateTableView();
        }
    }

    @FXML
    public void updateUser() {
        User selectedUser = tableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            // Получение данных из полей ввода
            selectedUser.setUsername(usernameField.getText());
            selectedUser.setPassword(passwordField.getText());
            selectedUser.setRole(roleField.getText());
            selectedUser.setEmail(emailField.getText());

            // Вызов метода updateUser в сервисе с указанием текущего пользователя
            userService.updateUser(selectedUser, currentUserService.getCurrentUser().getUsername());;

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
}
