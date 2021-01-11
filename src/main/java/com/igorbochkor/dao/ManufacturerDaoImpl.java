package com.igorbochkor.dao;

import com.igorbochkor.db.Storage;
import com.igorbochkor.lib.Dao;
import com.igorbochkor.model.Manufacturer;
import java.util.List;
import java.util.Optional;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        Storage.addManufacturer(manufacturer);
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long manufacturerId) {
        return Storage.manufacturers.stream()
                .filter(m -> m.getId().equals(manufacturerId))
                .findFirst();
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        get(manufacturer.getId()).get().setName(manufacturer.getName());
        get(manufacturer.getId()).get().setCountry(manufacturer.getCountry());
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.manufacturers.remove(get(id).get());
    }

    @Override
    public List<Manufacturer> getAllManufactures() {
        return Storage.manufacturers;
    }
}
