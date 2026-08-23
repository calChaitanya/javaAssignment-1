package com.calsoft.deviceConfigapi.dto;

import java.time.LocalDateTime;

public class DeviceConfigNotificationDTO {

    private Long deviceId;
    private String deviceIp;
    private String message;
    private LocalDateTime notifiedAt;

    public DeviceConfigNotificationDTO() {
    }

    public DeviceConfigNotificationDTO(Long deviceId, String deviceIp, String message,
            LocalDateTime notifiedAt) {
        this.deviceId = deviceId;
        this.deviceIp = deviceIp;
        this.message = message;
        this.notifiedAt = notifiedAt;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceIp() {
        return deviceIp;
    }

    public void setDeviceIp(String deviceIp) {
        this.deviceIp = deviceIp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getNotifiedAt() {
        return notifiedAt;
    }

    public void setNotifiedAt(LocalDateTime notifiedAt) {
        this.notifiedAt = notifiedAt;
    }
}