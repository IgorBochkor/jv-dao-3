package com.igorbochkor.dao.impl;

import com.igorbochkor.dao.CarDao;
import com.igorbochkor.db.Storage;
import com.igorbochkor.lib.Dao;
import com.igorbochkor.model.Car;
import com.igorbochkor.model.Driver;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Dao
public class CarDaoImpl implements CarDao {
    @Override
    public Car create(Car car) {
        Storage.addCar(car);
        return car;
    }

    @Override
    public Optional<Car> get(Long carId) {
        return Storage.cars.stream()
                .filter(c -> c.getId().equals(carId))
                .findFirst();
    }

    @Override
    public Car update(Car car) {
        IntStream.range(0, Storage.cars.size())
                .filter(c -> Storage.cars.get(c).getId().equals(car.getId()))
                .forEach(i -> Storage.cars.set(i, car));
        return car;
    }

    @Override
    public boolean delete(Long carId) {
        return Storage.cars.removeIf(c -> c.getId().equals(carId));
    }

    @Override
    public List<Car> getAllCars() {
        return Storage.cars;
    }

    @Override
    public List<Car> getAllByDriver(Long driverId) {
        List<Car> allCarsByDriverId = new ArrayList<>();
        for (int i = 0; i < Storage.cars.size(); i++) {
            Car car = Storage.cars.get(i);
            List<Driver> driverList = car.getDrivers();
            for (Driver driver : driverList) {
                if (driver.getId().equals(driverId)) {
                    allCarsByDriverId.add(car);
                }
            }
        }
        return allCarsByDriverId;
    }
}
