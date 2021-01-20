package com.igorbochkor.dao;

import com.igorbochkor.model.Car;
import java.util.List;

public interface CarDao extends GenericDao<Car, Long> {

    List<Car> getAllByDriver(Long driverId);

}
