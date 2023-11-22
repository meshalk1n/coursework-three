package com.example.service;

import com.example.entity.User;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {
    private User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}