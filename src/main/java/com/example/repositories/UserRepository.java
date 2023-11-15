package com.example.repositories;


import com.example.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface UserRepository extends CrudRepository<UserEntity, Integer> {
    List<UserEntity> findAll();
}