package com.example.controller;

import com.example.entity.User;
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
@FxmlView("/com.example.controller/second-main.fxml")
public class MainController {

    @FXML
    public TableColumn usernameColumn;

    @FXML
    public TableColumn passwordColumn;

    @FXML
    public TableColumn idColumn;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TableView<User> tableView;

    private final UserService userService;

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

    @FXML
    public void initialize() {
        // Связывание столбцов таблицы с полями объектов User
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        // Загрузка пользователей при инициализации
        updateTableView();
    }

    @FXML
    public void saveUser() {
        User newUser = new User();
        newUser.setUsername(usernameField.getText());
        newUser.setPassword(passwordField.getText());
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
            selectedUser.setUsername(usernameField.getText());
            selectedUser.setPassword(passwordField.getText());
            userService.updateUser(selectedUser);
            // Обновление отображения таблицы
            updateTableView();
        }
    }

    private void updateTableView() {
        List<User> users = userService.getAllUsers();
        tableView.getItems().setAll(users);
    }
}