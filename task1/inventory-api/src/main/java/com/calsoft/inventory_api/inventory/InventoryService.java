package com.calsoft.inventory_api.inventory;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public List<InventoryDetailsResponse> getInventoryDetails(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new InvalidDateRangeException("startDate must be on or before endDate");
        }
        return inventoryRepository.findByPurchaseDtBetweenOrderByPurchaseDtAscIdAsc(startDate, endDate)
                .stream()
                .map(InventoryDetailsResponse::from)
                .toList();
    }
}