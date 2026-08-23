# Device Configuration Notification API

## Tech stack

- Java 17
- Spring Boot 4.1.1
- Spring Web MVC
- Spring Data JPA / Hibernate
- MySQL
- Maven
- `RestTemplate` for webhook delivery

## Database setup

Run this SQL in MySQL. The database name must match `application.properties`:

```sql
CREATE DATABASE IF NOT EXISTS device_config;
USE device_config;

CREATE TABLE IF NOT EXISTS devices (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    device_ip VARCHAR(50) NOT NULL,
    device_details VARCHAR(255),
    config_changed BOOLEAN DEFAULT FALSE
);

INSERT INTO devices (device_ip, device_details, config_changed)
VALUES ('192.168.1.10', 'Configuration changed for testing', TRUE);

SELECT id, device_ip, device_details, config_changed
FROM devices
WHERE config_changed = TRUE;
```

Do not assume the inserted device has `id = 1`. Always check the generated ID using the `SELECT` query.

## Configuration

Edit `src/main/resources/application.properties`:

```properties
server.port=8081
spring.datasource.url=jdbc:mysql://localhost:3306/device_config
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
spring.jpa.hibernate.ddl-auto=update
```

The webhook URL is configured in `service/WebhookNotificationSender.java`.

## Run and test

Start the API:

```powershell
cd "C:\Users\chaitanya.luniya\Downloads\javaAssignment-1\task2\deviceConfigapi"
.\mvnw.cmd clean spring-boot:run
```

Wait for:

```text
Started DeviceConfigapiApplication
```

Trigger notification manually from a second terminal:

```powershell
curl.exe -i -X POST "http://localhost:8081/api/devices/deviceConfigNotification"
```

The response reports the number processed:

```json
{"processedDevices":1}
```

If it returns `{"processedDevices":0}`, run this SQL and immediately call the POST command again:

```sql
USE device_config;
UPDATE devices SET config_changed = TRUE;
SELECT id, config_changed FROM devices;
```

The API also checks automatically every 30 seconds because of `@Scheduled(fixedRate = 30000)`. Therefore, it may process the row before the manual POST. The row is reset to `FALSE` only after the webhook POST succeeds.

## Verify delivery

The API terminal should show:

```text
Found 1 device(s) with changed configuration
Sending device configuration notification for device <id>
Notification sent successfully for device <id> with HTTP status 2xx
Marked device <id> as notified
```

The same notification JSON should appear on Webhook.site. Finally verify the database:

```sql
SELECT id, device_ip, config_changed
FROM devices;
```

After successful delivery, `config_changed` should be `0` or `FALSE`.

## Classes

- `DeviceConfigapiApplication`: starts Spring Boot and enables scheduling.
- `entity/Device`: JPA mapping for the `devices` table.
- `dto/DeviceConfigNotificationDTO`: notification payload containing device ID, IP, message, and timestamp.
- `repository/DeviceRepository`: finds devices where `config_changed = TRUE`.
- `service/NotificationSender`: notification sender interface.
- `service/WebhookNotificationSender`: posts notifications to the configured webhook URL.
- `service/DeviceConfigNotificationService`: scheduled and transactional notification workflow.
- `controller/DeviceConfigController`: exposes the manual POST endpoint.

## Endpoint

```text
POST /api/devices/deviceConfigNotification
```

The endpoint returns HTTP `202 Accepted` with `processedDevices`, while the terminal logs and Webhook.site confirm actual delivery.
