package com.example.services;

import com.example.models.User;
import com.example.repositories.UserRepository;
import javafx.scene.control.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void saveUser(User user, String username) {
        // Проверка, занята ли электронная почта
        User existingUser = getUserByEmail(user.getEmail());
        if (existingUser != null) {
            // Если электронная почта уже занята, отобразить диалоговое окно
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText("Электронная почта уже занята!");

            alert.showAndWait();
        } else {
            // Если электронная почта свободна, сохранить пользователя
            user.setCreationDate(LocalDateTime.now());
            user.setAddedByUser(username);
            userRepository.save(user);
        }
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public void updateUser(User user, String modifiedBy) {
        // Обновление пользователя, только если у пользователя уже есть id
        if (user.getId() != 0) {
            // Проверка, занята ли электронная почта
            User existingUser = getUserByEmail(user.getEmail());
            if (existingUser != null && existingUser.getId() != user.getId()) {
                // Если электронная почта уже занята другим пользователем, отобразить диалоговое окно
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ошибка");
                alert.setHeaderText(null);
                alert.setContentText("Электронная почта уже занята!");

                alert.showAndWait();
            } else {
                // Если электронная почта свободна или принадлежит тому же пользователю, обновить пользователя
                user.setLastModifiedBy(modifiedBy);
                userRepository.save(user);
            }
        } else {
            // Можно добавить обработку, если пользователя с указанным id не существует.
            // Например, вывести сообщение об ошибке.
        }
    }


    public User getUserByUsernameAndPassword(String username, String password){
        return userRepository.findByUsernameAndPassword(username, password);
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public List<User> getUsersByRoleContains(String role) {
        return userRepository.findByRoleContains(role);
    }

    public List<User> getUsersByUsernameContains(String searchTerm){
        return userRepository.findByUsernameContains(searchTerm);
    }
}
