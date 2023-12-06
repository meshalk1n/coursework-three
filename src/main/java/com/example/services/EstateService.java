package com.example.services;

import com.example.models.Estate;
import com.example.repositories.EstateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EstateService {

    private final EstateRepository estateRepository;

    @Autowired
    public EstateService(EstateRepository estateRepository) {
        this.estateRepository = estateRepository;
    }

    public List<Estate> getAllEstate() {
        return estateRepository.findAll();
    }

    public void saveEstate(Estate estate) {
        estate.setAcquisitionDate(LocalDateTime.now());
        estateRepository.save(estate);
    }

    public void deleteEstate(Estate estate) {
        estateRepository.delete(estate);
    }

    public void updateUser(Estate estate, String modifiedBy) {
        // Обновление пользователя, только если у пользователя уже есть id
        if (estate.getId() != 0) {
            estate.setAddedByUser(modifiedBy);
            estateRepository.save(estate);
        } else {
            // Можете добавить обработку, если пользователя с указанным id не существует.
            // Например, вы можете вывести сообщение об ошибке.
        }
    }
}