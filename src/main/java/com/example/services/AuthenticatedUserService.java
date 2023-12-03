package com.example.services;

import com.example.models.User;
import org.springframework.stereotype.Service;

/*
TODO: сервис отвечает за управление текущим пользователем в приложении.
 Он предоставляет методы для получения и установки текущего пользователя.
 */

@Service
public class AuthenticatedUserService {
    private User activeUser;

    public User getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(User activeUser) {
        this.activeUser = activeUser;
    }
}
