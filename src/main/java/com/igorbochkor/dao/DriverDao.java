package com.igorbochkor.dao;

import com.igorbochkor.model.Driver;
import java.util.List;
import java.util.Optional;

public interface DriverDao {

    Driver create(Driver driver);

    Optional<Driver> get(Long driverId);

    Driver update(Driver driver);

    boolean delete(Long driverId);

    List<Driver> getAllDrivers();
}
