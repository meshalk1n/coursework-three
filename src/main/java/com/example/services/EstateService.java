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

    public void saveEstate(Estate estate, String username) {
        estate.setAcquisitionDate(LocalDateTime.now());
        estate.setAddedByUser(username);
        estateRepository.save(estate);
    }

    public void deleteEstate(Estate estate) {
        estateRepository.delete(estate);
    }

    public void updateEstate(Estate estate, String modifiedBy) {
        // Обновление пользователя, только если у пользователя уже есть id
        if (estate.getId() != 0) {
            estate.setLastModifiedBy(modifiedBy);
            estateRepository.save(estate);
        } else {
            // Можете добавить обработку, если пользователя с указанным id не существует.
            // Например, вы можете вывести сообщение об ошибке.
        }
    }

    public List<Estate> getEstateByNameContains(String searchTerm){
        return estateRepository.findByNameContains(searchTerm);
    }

    public List<Estate> getEstateByCategoryContains(String searchTerm){
        return estateRepository.findByCategoryContains(searchTerm);
    }

    public List<Estate> getEstateByCost(Integer searchTerm){
        return estateRepository.findByCost(searchTerm);
    }

    public List<Estate> getEstateByConditionContains(String searchTerm){
        return estateRepository.findByConditionContains(searchTerm);
    }
}
