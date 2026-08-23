# Inventory API

## Endpoint

`GET /api/inventory/details?startDate=2024-01-01&endDate=2024-12-31`

Both dates in `yyyy-MM-dd` format, inclusive. Returns one entry per inventory-detail row:

```json
[
  {
    "inventoryId": 1,
    "purchaseDt": "2024-03-10",
    "cost": 15000.00,
    "inventoryDetails": "Laptop batch - Dell Latitude x20 units"
  },
  {
    "inventoryId": 1,
    "purchaseDt": "2024-03-10",
    "cost": 15000.00,
    "inventoryDetails": "Includes extended warranty"
  }
]
```

## Classes

- `InventoryController` - handles the GET request and reads `startDate` / `endDate` as query params
- `InventoryService` / `InventoryServiceImpl` - validates the date range, builds the response
- `InventoryRepository` - fetches Inventory + InventoryDetails together using `JOIN FETCH`, avoids the N+1 query problem
- `Inventory` - maps to the `inventory` table
- `InventoryDetails` - maps to `inventory_details`, holds the FK back to Inventory
- `InventoryDetailsResponseDTO` - flat response object sent to the client, keeps entities from leaking out
- `GlobalExceptionHandler` - catches bad input (like startDate after endDate) and returns a clean 400 instead of a stack trace

## Run

MySQL should be running, with `calsoft_inventory` DB and tables already created (schema in `schema.sql`). Set your DB username/password in `application.properties`, then:

```powershell
.\mvnw.cmd spring-boot:run
```

Test it:

```powershell
curl "http://localhost:8080/api/inventory/details?startDate=2024-01-01&endDate=2024-12-31"
```

## Key learnings

- `JOIN FETCH` stops Hibernate from firing a separate query per row when loading related data
- DTOs keep the API response shape independent of the DB schema
- `@RestControllerAdvice` centralizes error handling instead of try-catch in every method
- Query params fit better than path variables for filters like date ranges