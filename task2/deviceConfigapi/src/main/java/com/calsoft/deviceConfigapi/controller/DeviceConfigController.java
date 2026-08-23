package com.calsoft.deviceConfigapi.controller;

import com.calsoft.deviceConfigapi.service.DeviceConfigNotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/devices")
public class DeviceConfigController {

    private final DeviceConfigNotificationService notificationService;

    public DeviceConfigController(DeviceConfigNotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/deviceConfigNotification")
    public ResponseEntity<NotificationResult> deviceConfigNotification() {
        int processedDevices = notificationService.deviceConfigNotification();
        return ResponseEntity.accepted().body(new NotificationResult(processedDevices));
    }

    public record NotificationResult(int processedDevices) {
    }
}