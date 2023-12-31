package com.example.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "role")
    private String role;

    @Column(name = "added_by_user")
    private String addedByUser;

    public User(){

    }

    public User(String username, String password, String role, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public String getAddedByUser() {
        return addedByUser;
    }

    public void setAddedByUser(String addedByUser) {
        this.addedByUser = addedByUser;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(username, user.username) && Objects.equals(password,
                user.password) && Objects.equals(email, user.email) && Objects.equals(creationDate,
                user.creationDate) && Objects.equals(lastModifiedBy, user.lastModifiedBy) && Objects.equals(role,
                user.role) && Objects.equals(addedByUser, user.addedByUser);
    }

    @Override
    public String toString() {
        return id + username + password + role + creationDate + lastModifiedBy + addedByUser;
    }
}
