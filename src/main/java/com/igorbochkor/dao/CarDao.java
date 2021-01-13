package com.igorbochkor.dao;

import com.igorbochkor.model.Car;
import java.util.List;
import java.util.Optional;

public interface CarDao {

    Car create(Car car);

    Optional<Car> get(Long carId);

    Car update(Car car);

    boolean delete(Long carId);

    List<Car> getAllCars();

    List<Car> getAllByDriver(Long driverId);
}
