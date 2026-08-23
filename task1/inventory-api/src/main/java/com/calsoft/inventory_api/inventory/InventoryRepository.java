package com.calsoft.inventory_api.inventory;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    @EntityGraph(attributePaths = "details")
    List<Inventory> findByPurchaseDtBetweenOrderByPurchaseDtAscIdAsc(
            LocalDate startDate, LocalDate endDate);
}