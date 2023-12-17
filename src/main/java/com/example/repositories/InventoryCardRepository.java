package com.example.repositories;

import com.example.models.InventoryCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryCardRepository  extends JpaRepository<InventoryCard, Integer> {

    List<InventoryCard> findByStatusContains(String searchTerm);
}
