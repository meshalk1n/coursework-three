package com.example.services;

import com.example.models.Estate;
import com.example.models.InventoryCard;
import com.example.repositories.InventoryCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InventoryCardService {

    private final InventoryCardRepository inventoryCardRepository;

    @Autowired
    public InventoryCardService(InventoryCardRepository inventoryCardRepository) {
        this.inventoryCardRepository = inventoryCardRepository;
    }

    public List<InventoryCard> getAllInventoryCard() {
        return inventoryCardRepository.findAll();
    }

    public void saveInventoryCard(InventoryCard inventoryCard, String username) {
        inventoryCard.setInventoryDate(LocalDateTime.now());
        inventoryCard.setInventoryOfficer(username);
        inventoryCardRepository.save(inventoryCard);
    }

    public void deleteInventoryCard(InventoryCard inventoryCard) {
        inventoryCardRepository.delete(inventoryCard);
    }

    public void updateInventoryCard(InventoryCard inventoryCard, String modifiedBy) {
        // Обновление пользователя, только если у пользователя уже есть id
        if (inventoryCard.getId() != 0) {
            inventoryCard.setInventoryOfficer(modifiedBy);
            inventoryCardRepository.save(inventoryCard);
        } else {
            // Можно добавить обработку, если пользователя с указанным id не существует.
            // Например, вывести сообщение об ошибке.
        }
    }

    public List<InventoryCard> getEstateByStatusContains(String searchTerm){
        return inventoryCardRepository.findByStatusContains(searchTerm);
    }

    public boolean getExistsByInventoryCard(Estate estate){
        return inventoryCardRepository.existsByEstate(estate);
    }

    public List<InventoryCard> getEstateByLocationContains(String searchTerm){
        return inventoryCardRepository.findByLocationContains(searchTerm);
    }
}
