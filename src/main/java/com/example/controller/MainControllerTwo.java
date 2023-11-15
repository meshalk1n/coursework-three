package com.example.controller;

import com.example.entity.UserEntity;
import com.example.repositories.UserRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@FxmlView("/com.example.controller/second-main.fxml")
public class MainControllerTwo {

    private final UserRepository userRepository;
    private final ObservableList<UserEntity> userList;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private ListView<UserEntity> listView;

    @Autowired
    public MainControllerTwo(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userList = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize() {
        // Загрузка пользователей при инициализации
        userList.setAll(userRepository.findAll());
        listView.setItems(userList);
    }

    @FXML
    public void saveUser() {
        UserEntity newUserEntity = new UserEntity();
        newUserEntity.setUsername(usernameField.getText());
        newUserEntity.setPassword(passwordField.getText());
        userRepository.save(newUserEntity);

        // обновление отображения списка
        userList.setAll(userRepository.findAll());
    }
}
