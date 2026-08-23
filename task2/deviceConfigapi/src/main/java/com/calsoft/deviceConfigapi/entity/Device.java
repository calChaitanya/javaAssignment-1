package com.calsoft.deviceConfigapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "devices")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String deviceIp;

    private String deviceDetails;

    private Boolean configChanged;

    protected Device() {
    }

    public Long getId() {
        return id;
    }

    public String getDeviceIp() {
        return deviceIp;
    }

    public String getDeviceDetails() {
        return deviceDetails;
    }

    public Boolean getConfigChanged() {
        return configChanged;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDeviceIp(String deviceIp) {
        this.deviceIp = deviceIp;
    }

    public void setDeviceDetails(String deviceDetails) {
        this.deviceDetails = deviceDetails;
    }

    public void setConfigChanged(Boolean configChanged) {
        this.configChanged = configChanged;
    }
}