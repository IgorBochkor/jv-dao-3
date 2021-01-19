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
        String getQueryById = "SELECT c.car_id, c.model, m.manufacture_id,m.name, m.country "
                + "FROM cars c "
                + "INNER JOIN manufactures m ON c.manufacturer_id  = m.manufacture_id "
                + "WHERE car_id = ?  AND c.deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement statement
                = connection.prepareStatement(getQueryById)) {
            statement.setLong(1, carId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                car = getCar(resultSet);
                if (car != null) {
                    car.setDrivers(getDriversFromCar(connection, car.getId()));
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get car by id = "
                    + carId, e);
        }
        return Optional.ofNullable(car);
    }

    @Override
    public Car update(Car car) {
        String updateQuery = "UPDATE cars SET model = ?, manufacturer_id = ? "
                + "WHERE car_id = ? AND deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement statement
                = connection.prepareStatement(updateQuery)) {
            statement.setString(1, car.getModel());
            statement.setLong(2, car.getManufacturer().getId());
            statement.setLong(3, car.getId());
            statement.executeUpdate();
            deleteCarDrivers(connection, car.getId());
            addDriversToCar(connection, car);
            return car;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update car by id = "
                    + car.getId(), e);
        }
    }

    @Override
    public List<Car> getAllCars() {
        List<Car> carList = new ArrayList<>();
        String getAllQueryById = "SELECT c.car_id, c.model, m.manufacture_id,m.name, m.country "
                + "FROM cars c "
                + "INNER JOIN manufactures m ON c.manufacturer_id  = m.manufacture_id "
                + "WHERE c.deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement statement
                = connection.prepareStatement(getAllQueryById)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Car car = getCar(resultSet);
                car.setDrivers(getDriversFromCar(connection, car.getId()));
                carList.add(car);
            }
            return carList;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all cars!", e);
        }
    }

    @Override
    public boolean delete(Long carId) {
        String deleteQuery = "UPDATE cars SET deleted = TRUE WHERE car_id = ?";
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement statement
                = connection.prepareStatement(deleteQuery)) {
            statement.setLong(1, carId);
            int executeUpdate = statement.executeUpdate();
            return executeUpdate > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete car by id = "
                    + carId, e);
        }
    }

    @Override
    public List<Car> getAllByDriver(Long driverId) {
        List<Car> carList = new ArrayList<>();
        String getAllByDriverQuery = "SELECT c.car_id, c.model, m.manufacture_id,"
                + "m.name, m.country FROM cars_drivers cd "
                + "INNER JOIN  cars c ON cd.car_id = c.car_id "
                + "INNER JOIN manufactures m ON c.manufacturer_id = m.manufacture_id "
                + "INNER JOIN drivers d ON d.driver_id = cd.driver_id "
                + "WHERE cd.driver_id = ? AND c.deleted = FALSE AND d.deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement statement
                = connection.prepareStatement(getAllByDriverQuery)) {
            statement.setLong(1, driverId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Car car = getCar(resultSet);
                car.setDrivers(getDriversFromCar(connection, car.getId()));
                carList.add(car);
            }
            return carList;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get car by DriverId = "
                    + driverId, e);
        }
    }

    private List<Driver> getDriversFromCar(Connection connection, Long carId) throws SQLException {
        String getDriversQuery = "SELECT d.driver_id, d.name, d.lisence_number"
                + " FROM cars_drivers cd INNER JOIN drivers d ON cd.driver_id = d.driver_id "
                + "WHERE cd.car_id = ? AND d.deleted = FALSE";
        PreparedStatement statement = connection.prepareStatement(getDriversQuery);
        statement.setLong(1, carId);
        ResultSet resultSet = statement.executeQuery();
        List<Driver> driverList = new ArrayList<>();
        while (resultSet.next()) {
            Long driverID = resultSet.getObject("driver_id", Long.class);
            String driverName = resultSet.getObject("name", String.class);
            String lisenceNumber = resultSet.getObject("lisence_number", String.class);
            Driver driver = new Driver(driverName, lisenceNumber);
            driver.setId(driverID);
            driverList.add(driver);
        }
        return driverList;
    }

    private Car getCar(ResultSet resultSet) throws SQLException {
        Car car;
        Long id = resultSet.getObject("car_id", Long.class);
        String model = resultSet.getObject("model", String.class);
        Long manufactureId = resultSet.getObject("manufacture_id", Long.class);
        String manufacturerName = resultSet.getObject("name", String.class);
        String manufacturerCountry = resultSet.getObject("country", String.class);
        Manufacturer manufacturer = new Manufacturer(manufacturerName, manufacturerCountry);
        manufacturer.setId(manufactureId);
        car = new Car(model, manufacturer);
        car.setId(id);
        return car;
    }

    private void deleteCarDrivers(Connection connection, Long carID) throws SQLException {
        String deleteCarDriversQuery = "DELETE FROM cars_drivers WHERE car_id = ?";
        PreparedStatement statement = connection.prepareStatement(deleteCarDriversQuery);
        statement.setLong(1, carID);
        statement.executeUpdate();
    }

    private void addDriversToCar(Connection connection, Car car) throws SQLException {
        String addCarDriversQuery = "INSERT cars_drivers (driver_id, car_id) VALUES (?,?)";
        PreparedStatement statement = connection.prepareStatement(addCarDriversQuery);
        List<Driver> driverList = car.getDrivers();
        for (Driver drivers : driverList) {
            statement.setLong(1, drivers.getId());
            statement.setLong(2, car.getId());
            statement.executeUpdate();
        }
    }
}
