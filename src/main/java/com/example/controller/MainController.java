package com.example.controller;

import com.example.entity.User;
import com.example.repositories.UserRepository;
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

    private final UserRepository userRepository;

    @Autowired
    public MainController(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        userRepository.save(newUser);

        // Обновление отображения списка
        updateListView();
    }


    @FXML
    public void deleteUser() {
        User selectedUser = listView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            userRepository.delete(selectedUser);
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
            userRepository.save(selectedUser);
            // Обновление отображения списка
            updateListView();
        }
    }

    private void updateListView() {
        List<User> users = userRepository.findAll();
        listView.getItems().setAll(users);
    }
}
