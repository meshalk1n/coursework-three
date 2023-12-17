package com.example.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "inventory_card")
public class InventoryCard {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "estate_id", referencedColumnName = "id")
    private Estate estate;

    @Column(name = "inventory_date")
    private LocalDateTime inventoryDate;

    @Column(name = "inventory_officer")
    private String inventoryOfficer;

    @Column(name = "notes")
    private String notes;

    @Column(name = "location")
    private String location;

    @Column(name = "status")
    private String status;

    public InventoryCard(){}

    public InventoryCard(String inventoryOfficer, String notes, String location, String status) {
        this.inventoryOfficer = inventoryOfficer;
        this.notes = notes;
        this.location = location;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Estate getEstate() {
        return estate;
    }

    public void setEstate(Estate estate) {
        this.estate = estate;
    }

    public LocalDateTime getInventoryDate() {
        return inventoryDate;
    }

    public void setInventoryDate(LocalDateTime inventoryDate) {
        this.inventoryDate = inventoryDate;
    }

    public String getInventoryOfficer() {
        return inventoryOfficer;
    }

    public void setInventoryOfficer(String inventoryOfficer) {
        this.inventoryOfficer = inventoryOfficer;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventoryCard that = (InventoryCard) o;
        return id == that.id && Objects.equals(estate,
                that.estate) && Objects.equals(inventoryDate,
                that.inventoryDate) && Objects.equals(inventoryOfficer,
                that.inventoryOfficer) && Objects.equals(notes,
                that.notes) && Objects.equals(location,
                that.location) && Objects.equals(status,
                that.status);
    }

    @Override
    public String toString() {
        return String.valueOf(id) + (estate != null ? estate.toString() : "") +
                inventoryDate + inventoryOfficer + notes + location + status;
    }
}
