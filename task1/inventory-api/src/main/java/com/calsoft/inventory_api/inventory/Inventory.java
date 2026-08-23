package com.calsoft.inventory_api.inventory;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate purchaseDt;

    private BigDecimal cost;

    @OneToMany(mappedBy = "inventory")
    private List<InventoryDetails> details = new ArrayList<>();

    protected Inventory() {
    }

    public Long getId() {
        return id;
    }

    public LocalDate getPurchaseDt() {
        return purchaseDt;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public List<InventoryDetails> getDetails() {
        return details;
    }
}