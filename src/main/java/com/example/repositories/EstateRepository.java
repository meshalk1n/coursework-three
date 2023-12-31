package com.example.repositories;

import com.example.models.Estate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstateRepository extends JpaRepository<Estate, Integer> {

    List<Estate> findByNameContains(String searchTerm);

    List<Estate> findByCategoryContains(String searchTerm);

    List<Estate> findByCost(Integer searchTerm);

    List<Estate> findByConditionContains(String searchTerm);
}
