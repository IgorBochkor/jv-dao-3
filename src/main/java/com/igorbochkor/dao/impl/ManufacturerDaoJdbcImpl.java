package com.igorbochkor.dao.impl;

import com.igorbochkor.dao.ManufacturerDao;
import com.igorbochkor.exception.DataProcessingException;
import com.igorbochkor.lib.Dao;
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
public class ManufacturerDaoJdbcImpl implements ManufacturerDao {

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String query = "INSERT INTO manufactures (name, country) VALUE (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement
                    = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                manufacturer.setId(resultSet.getLong(1));
            }
            statement.close();
            resultSet.close();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create manufacturer " + manufacturer, e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long manufacturerId) {
        Manufacturer manufacturer = null;
        String query = "SELECT * FROM manufactures WHERE manufacture_id = ? AND deleted = false";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, manufacturerId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                manufacturer = getManufacturer(resultSet);
            }
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer by id = "
                    + manufacturerId, e);
        }
        return Optional.ofNullable(manufacturer);
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String query = "UPDATE manufactures SET name = ?, country = ? "
                + "WHERE manufacture_id = ? AND deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.setLong(3, manufacturer.getId());
            statement.executeUpdate();
            statement.close();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer by id = "
                    + manufacturer.getId(), e);
        }
    }

    @Override
    public boolean delete(Long manufacturerId) {
        String query = "UPDATE manufactures SET deleted = TRUE WHERE manufacture_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, manufacturerId);
            int updateRows = statement.executeUpdate();
            statement.close();
            return updateRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer by id = "
                    + manufacturerId, e);
        }
    }

    @Override
    public List<Manufacturer> getAllManufactures() {
        List<Manufacturer> listManufacture = new ArrayList<>();
        String query = "SELECT * FROM manufactures WHERE deleted = false";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                listManufacture.add(getManufacturer(resultSet));
            }
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from db!", e);
        }
        return listManufacture;
    }

    private Manufacturer getManufacturer(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer;
        Long manufacturerId = resultSet.getObject("manufacture_id", Long.class);
        String name = resultSet.getObject("name", String.class);
        String country = resultSet.getObject("country", String.class);
        manufacturer = new Manufacturer(name, country);
        manufacturer.setId(manufacturerId);
        return manufacturer;
    }
}
