package com.igorbochkor.dao.impl;

import com.igorbochkor.dao.DriverDao;
import com.igorbochkor.db.Storage;
import com.igorbochkor.lib.Dao;
import com.igorbochkor.model.Driver;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Dao
public class DriverCarImpl implements DriverDao {
    @Override
    public Driver create(Driver driver) {
        Storage.addDriver(driver);
        return driver;
    }

    @Override
    public Optional<Driver> get(Long driverId) {
        return Storage.drivers.stream()
                .filter(d -> d.getId().equals(driverId))
                .findFirst();
    }

    @Override
    public Driver update(Driver driver) {
        IntStream.range(0, Storage.drivers.size())
                .filter(d -> Storage.drivers.get(d).getId().equals(driver.getId()))
                .forEach(i -> Storage.drivers.set(i, driver));
        return driver;
    }

    @Override
    public boolean delete(Long driverId) {
        return Storage.drivers.removeIf(d -> d.getId().equals(driverId));
    }

    @Override
    public List<Driver> getAllDrivers() {
        return Storage.drivers;
    }
}
