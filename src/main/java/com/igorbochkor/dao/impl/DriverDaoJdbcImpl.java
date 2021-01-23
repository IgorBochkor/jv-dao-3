package com.igorbochkor.dao.impl;

import com.igorbochkor.dao.DriverDao;
import com.igorbochkor.exception.DataProcessingException;
import com.igorbochkor.lib.Dao;
import com.igorbochkor.model.Driver;
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
public class DriverDaoJdbcImpl implements DriverDao {
    @Override
    public Driver create(Driver driver) {
        String createQuery = "INSERT INTO drivers (name, lisence_number, login, password)"
                + " VALUE (?, ?, ?, ?)";
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement statement
                = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, driver.getName());
            statement.setString(2, driver.getLicenceNumber());
            statement.setString(3, driver.getLogin());
            statement.setString(4, driver.getPassword());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                driver.setId(resultSet.getObject(1, Long.class));
            }
            return driver;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create driver " + driver, e);
        }
    }

    @Override
    public Optional<Driver> get(Long driverId) {
        Driver driver = null;
        String getQueryById = "SELECT * FROM drivers WHERE id = ? AND deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement statement
                = connection.prepareStatement(getQueryById)) {
            statement.setLong(1,driverId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                driver = getDriver(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get driver by id = "
                    + driverId, e);
        }
        return Optional.ofNullable(driver);
    }

    @Override
    public Optional<Driver> findByLogin(String login) {
        Driver driver = null;
        String getQueryById = "SELECT * FROM drivers WHERE login = ? AND deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement statement
                = connection.prepareStatement(getQueryById)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                driver = getDriver(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get driver by login = "
                    + login, e);
        }
        return Optional.ofNullable(driver);
    }

    @Override
    public Driver update(Driver driver) {
        String updateQuery = "UPDATE drivers SET name = ?, lisence_number = ? "
                + "WHERE id = ? AND deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement statement
                = connection.prepareStatement(updateQuery)) {
            statement.setString(1,driver.getName());
            statement.setString(2,driver.getLicenceNumber());
            statement.setLong(3,driver.getId());
            statement.executeUpdate();
            return driver;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update driver by id = "
                    + driver.getId(), e);
        }
    }

    @Override
    public boolean delete(Long driverId) {
        String deleteQuery = "UPDATE drivers SET deleted = TRUE WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement statement
                = connection.prepareStatement(deleteQuery)) {
            statement.setLong(1, driverId);
            int executeUpdate = statement.executeUpdate();
            return executeUpdate > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete driver by id = "
                    + driverId, e);
        }
    }

    @Override
    public List<Driver> getAll() {
        List<Driver> drivers = new ArrayList<>();
        String getAllDriversQuery = "SELECT * FROM drivers WHERE deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement statement
                = connection.prepareStatement(getAllDriversQuery)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                drivers.add(getDriver(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all drivers from db!", e);
        }
        return drivers;
    }

    private Driver getDriver(ResultSet resultSet) throws SQLException {
        Driver driver;
        Long driverId = resultSet.getObject("id", Long.class);
        String name = resultSet.getObject("name", String.class);
        String lisenceNumber = resultSet.getObject("lisence_number", String.class);
        String login = resultSet.getObject("login", String.class);
        String password = resultSet.getObject("password", String.class);
        driver = new Driver(name,lisenceNumber,login,password);
        driver.setId(driverId);
        return driver;
    }
}
