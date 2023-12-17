package com.example.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "estate")
public class Estate {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "category")
    private String category;

    @Column(name = "cost")
    private int cost;

    @Column(name = "acquisition_date")
    private LocalDateTime acquisitionDate;

    @Column(name = "condition")
    private String condition;

    @Column(name = "added_by_user")
    private String addedByUser;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @OneToOne(mappedBy = "estate")
    private InventoryCard inventoryCard;

    public Estate(){

    }

    public Estate(String name, String category, int cost, String condition) {
        this.name = name;
        this.category = category;
        this.cost = cost;
        this.condition = condition;
    }

    public InventoryCard getInventoryCard() {
        return inventoryCard;
    }

    public void setInventoryCard(InventoryCard inventoryCard) {
        this.inventoryCard = inventoryCard;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public LocalDateTime getAcquisitionDate() {
        return acquisitionDate;
    }

    public void setAcquisitionDate(LocalDateTime acquisitionDate) {
        this.acquisitionDate = acquisitionDate;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getAddedByUser() {
        return addedByUser;
    }

    public void setAddedByUser(String addedByUser) {
        this.addedByUser = addedByUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Estate estate = (Estate) o;
        return id == estate.id && cost == estate.cost && Objects.equals(name,
                estate.name) && Objects.equals(category,
                estate.category) && Objects.equals(acquisitionDate,
                estate.acquisitionDate) && Objects.equals(condition,
                estate.condition) && Objects.equals(addedByUser,
                estate.addedByUser) && Objects.equals(lastModifiedBy,
                estate.lastModifiedBy) && Objects.equals(inventoryCard,
                estate.inventoryCard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, category, cost, acquisitionDate,
                condition, addedByUser, lastModifiedBy, inventoryCard);
    }

    @Override
    public String toString() {
        return id + name + category + cost + acquisitionDate +
                condition + addedByUser + lastModifiedBy + (inventoryCard != null ? inventoryCard.toString() : "");
    }
}
