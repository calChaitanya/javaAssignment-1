package com.calsoft.deviceConfigapi.repository;

import com.calsoft.deviceConfigapi.entity.Device;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    List<Device> findByConfigChangedTrue();
}