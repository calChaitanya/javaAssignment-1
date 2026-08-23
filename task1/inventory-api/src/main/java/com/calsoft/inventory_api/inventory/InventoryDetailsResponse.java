package com.calsoft.inventory_api.inventory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record InventoryDetailsResponse(
        Long id,
        LocalDate purchaseDate,
        BigDecimal cost,
        List<String> inventoryDetails) {

    static InventoryDetailsResponse from(Inventory inventory) {
        List<String> descriptions = inventory.getDetails().stream()
                .map(InventoryDetails::getDescription)
                .toList();
        return new InventoryDetailsResponse(
                inventory.getId(), inventory.getPurchaseDt(), inventory.getCost(), descriptions);
    }
}