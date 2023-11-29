package com.example.service;

import com.example.entity.User;
import com.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
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

    public void saveUser(User user) {
        user.setCreationDate(LocalDateTime.now());
        userRepository.save(user);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public void updateUser(User user, String modifiedBy) {
        // Обновление пользователя, только если у пользователя уже есть id
        if (user.getId() != 0) {
            user.setLastModifiedBy(modifiedBy);
            userRepository.save(user);
        } else {
            // Можете добавить обработку, если пользователя с указанным id не существует.
            // Например, вы можете вывести сообщение об ошибке.
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
}
