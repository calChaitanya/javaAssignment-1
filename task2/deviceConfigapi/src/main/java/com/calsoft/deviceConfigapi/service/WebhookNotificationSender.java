package com.calsoft.deviceConfigapi.service;

import com.calsoft.deviceConfigapi.dto.DeviceConfigNotificationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Component
public class WebhookNotificationSender implements NotificationSender {

    private static final String WEBHOOK_URL = "https://webhook.site/25e5550e-7ed5-4fc2-91cf-c9bae716ea16";
    private static final Logger logger = LoggerFactory.getLogger(WebhookNotificationSender.class);

    private final RestTemplate restTemplate;

    public WebhookNotificationSender(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void send(DeviceConfigNotificationDTO notification) {
        logger.info("Sending device configuration notification for device {}", notification.getDeviceId());
        ResponseEntity<String> response = restTemplate.postForEntity(WEBHOOK_URL, notification, String.class);
        logger.info("Notification sent successfully for device {} with HTTP status {}",
                notification.getDeviceId(), response.getStatusCode().value());
    }
}