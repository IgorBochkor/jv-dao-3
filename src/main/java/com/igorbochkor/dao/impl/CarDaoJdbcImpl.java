package com.igorbochkor.dao.impl;

import com.igorbochkor.dao.CarDao;
import com.igorbochkor.exception.DataProcessingException;
import com.igorbochkor.lib.Dao;
import com.igorbochkor.model.Car;
import com.igorbochkor.model.Driver;
import com.igorbochkor.model.Manufacturer;
import com.igorbochkor.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class CarDaoJdbcImpl implements CarDao {
    @Override
    public Car create(Car car) {
        String createQuery = "INSERT INTO cars (model, manufacturer_id) VALUE (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement statement
                = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, car.getModel());
            statement.setLong(2, car.getManufacturer().getId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                car.setId(resultSet.getObject(1, Long.class));
            }
            return car;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create car " + car, e);
        }
    }

    @Override
    public Optional<Car> get(Long carId) {
        Car car = null;
        String getQueryById = "SELECT c.id, c.model, m.id,m.name, m.country "
                + "FROM cars c "
                + "INNER JOIN manufactures m ON c.manufacturer_id  = m.id "
                + "WHERE c.id = ?  AND c.deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement statement
                = connection.prepareStatement(getQueryById)) {
            statement.setLong(1, carId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                car = getCar(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get car by id = "
                    + carId, e);
        }
        if (car != null) {
            car.setDrivers(getDriversFromCar(car.getId()));
        }
        return Optional.ofNullable(car);
    }

    @Override
    public Car update(Car car) {
        String updateQuery = "UPDATE cars SET model = ?, manufacturer_id = ? "
                + "WHERE id = ? AND deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement statement
                = connection.prepareStatement(updateQuery)) {
            statement.setString(1, car.getModel());
            statement.setLong(2, car.getManufacturer().getId());
            statement.setLong(3, car.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update car by id = "
                    + car.getId(), e);
        }
        deleteCarDrivers(car.getId());
        addDriversToCar(car);
        return car;
    }

    @Override
    public List<Car> getAllCars() {
        List<Car> carList = new ArrayList<>();
        String getAllQueryById = "SELECT c.id, c.model, m.id,m.name, m.country "
                + "FROM cars c "
                + "INNER JOIN manufactures m ON c.manufacturer_id  = m.id "
                + "WHERE c.deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement statement
                = connection.prepareStatement(getAllQueryById)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Car car = getCar(resultSet);
                carList.add(car);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all cars!", e);
        }
        for (Car car : carList) {
            car.setDrivers(getDriversFromCar(car.getId()));
        }
        return carList;
    }

    @Override
    public boolean delete(Long carId) {
        String deleteQuery = "UPDATE cars SET deleted = TRUE WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement statement
                = connection.prepareStatement(deleteQuery)) {
            statement.setLong(1, carId);
            int executeUpdate = statement.executeUpdate();
            return executeUpdate > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete car by id = " + carId, e);
        }
    }

    @Override
    public List<Car> getAllByDriver(Long driverId) {
        List<Car> carList = new ArrayList<>();
        String getAllByDriverQuery = "SELECT c.id, c.model, m.id,"
                + "m.name, m.country FROM cars_drivers cd "
                + "INNER JOIN  cars c ON cd.car_id = c.id "
                + "INNER JOIN manufactures m ON c.manufacturer_id = m.id "
                + "INNER JOIN drivers d ON d.id = cd.driver_id "
                + "WHERE cd.driver_id = ? AND c.deleted = FALSE AND d.deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement statement
                = connection.prepareStatement(getAllByDriverQuery)) {
            statement.setLong(1, driverId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Car car = getCar(resultSet);
                carList.add(car);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get car by DriverId = " + driverId, e);
        }
        for (Car car : carList) {
            car.setDrivers(getDriversFromCar(car.getId()));
        }
        return carList;
    }

    private List<Driver> getDriversFromCar(Long carId) {
        String getDriversQuery = "SELECT d.id, d.name, d.lisence_number"
                + " FROM cars_drivers cd INNER JOIN drivers d ON cd.driver_id = d.id "
                + "WHERE cd.car_id = ? AND d.deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement statement
                = connection.prepareStatement(getDriversQuery)) {
            statement.setLong(1, carId);
            ResultSet resultSet = statement.executeQuery();
            List<Driver> driverList = new ArrayList<>();
            while (resultSet.next()) {
                Long driverId = resultSet.getObject("id", Long.class);
                String driverName = resultSet.getObject("name", String.class);
                String lisenceNumber = resultSet.getObject("lisence_number", String.class);
                Driver driver = new Driver(driverName, lisenceNumber);
                driver.setId(driverId);
                driverList.add(driver);
            }
            return driverList;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get drivers from car by CarId = " + carId, e);
        }
    }

    private Car getCar(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String model = resultSet.getObject("model", String.class);
        Long manufactureId = resultSet.getObject("m.id", Long.class);
        String manufacturerName = resultSet.getObject("name", String.class);
        String manufacturerCountry = resultSet.getObject("country", String.class);
        Manufacturer manufacturer = new Manufacturer(manufacturerName, manufacturerCountry);
        manufacturer.setId(manufactureId);
        Car car = new Car(model, manufacturer);
        car.setId(id);
        return car;
    }

    private void deleteCarDrivers(Long carId) {
        String deleteCarDriversQuery = "DELETE FROM cars_drivers WHERE car_id = ?";
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement statement
                = connection.prepareStatement(deleteCarDriversQuery)) {
            statement.setLong(1, carId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete drivers from car by CarId = "
                    + carId, e);
        }
    }

    private void addDriversToCar(Car car) {
        String addCarDriversQuery = "INSERT cars_drivers (driver_id, car_id) VALUES (?,?)";
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement statement
                = connection.prepareStatement(addCarDriversQuery)) {
            List<Driver> driverList = car.getDrivers();
            for (Driver drivers : driverList) {
                statement.setLong(1, drivers.getId());
                statement.setLong(2, car.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add drivers to car" + car, e);
        }
    }
}
