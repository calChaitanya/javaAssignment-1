package com.calsoft.deviceConfigapi.service;

import com.calsoft.deviceConfigapi.dto.DeviceConfigNotificationDTO;
import com.calsoft.deviceConfigapi.entity.Device;
import com.calsoft.deviceConfigapi.repository.DeviceRepository;
import java.time.LocalDateTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeviceConfigNotificationService {

    private static final Logger logger = LoggerFactory.getLogger(DeviceConfigNotificationService.class);

    private final DeviceRepository deviceRepository;
    private final NotificationSender notificationSender;

    public DeviceConfigNotificationService(DeviceRepository deviceRepository,
            NotificationSender notificationSender) {
        this.deviceRepository = deviceRepository;
        this.notificationSender = notificationSender;
    }

    @Scheduled(fixedRate = 30000)
    @Transactional
    public int deviceConfigNotification() {
        var changedDevices = deviceRepository.findByConfigChangedTrue();
        logger.info("Found {} device(s) with changed configuration", changedDevices.size());
        for (Device device : changedDevices) {
            DeviceConfigNotificationDTO notification = new DeviceConfigNotificationDTO(
                    device.getId(),
                    device.getDeviceIp(),
                    device.getDeviceDetails(),
                    LocalDateTime.now());
            notificationSender.send(notification);
            device.setConfigChanged(false);
            deviceRepository.save(device);
            logger.info("Marked device {} as notified", device.getId());
        }
        return changedDevices.size();
    }
}