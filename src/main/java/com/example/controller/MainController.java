package com.example.controller;

import com.example.entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("/com.example.controller/second-main.fxml")
public class MainController {

    @FXML
    public TextField usernameField;

    @FXML
    public TextField passwordField;

    @FXML
    private ListView<User> listView;

    private ObservableList<User> userList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Инициализация элементов управления и данных
        listView.setItems(userList);
    }

    @FXML
    public void saveUser() {
        User newUser = new User();
        newUser.setUsername(usernameField.getText());
        newUser.setPassword(passwordField.getText());
        userList.add(newUser);
    }

    @FXML
    public void deleteUser() {
        int selectedIndex = listView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            userList.remove(selectedIndex);
        }
    }

    @FXML
    public void updateUser() {
        int selectedIndex = listView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            User selectedUser = userList.get(selectedIndex);
            selectedUser.setUsername(usernameField.getText());
            selectedUser.setPassword(passwordField.getText());
            listView.refresh();
        }
    }
}