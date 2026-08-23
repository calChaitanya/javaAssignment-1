package com.calsoft.deviceConfigapi.service;

import com.calsoft.deviceConfigapi.dto.DeviceConfigNotificationDTO;

public interface NotificationSender {

    void send(DeviceConfigNotificationDTO notification);
}