package com.example.controller;

import com.example.entity.User;
import com.example.service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@FxmlView("/com.example.controller/second-main.fxml")
public class MainController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private ListView<User> listView;

    private final UserService userService;

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }


    @FXML
    public void initialize() {
        // Загрузка пользователей при инициализации
        updateListView();
    }

    @FXML
    public void saveUser() {
        User newUser = new User();
        newUser.setUsername(usernameField.getText());
        newUser.setPassword(passwordField.getText());
        userService.saveUser(newUser);
        // Обновление отображения списка
        updateListView();
    }


    @FXML
    public void deleteUser() {
        User selectedUser = listView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            userService.deleteUser(selectedUser);
            // Обновление отображения списка
            updateListView();
        }
    }

    @FXML
    public void updateUser() {
        User selectedUser = listView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            selectedUser.setUsername(usernameField.getText());
            selectedUser.setPassword(passwordField.getText());
            userService.updateUser(selectedUser);
            // Обновление отображения списка
            updateListView();
        }
    }

    private void updateListView() {
        List<User> users = userService.getAllUsers();
        listView.getItems().setAll(users);
    }
}
