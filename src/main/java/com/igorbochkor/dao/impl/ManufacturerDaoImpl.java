package com.igorbochkor.dao.impl;

import com.igorbochkor.dao.ManufacturerDao;
import com.igorbochkor.db.Storage;
import com.igorbochkor.model.Manufacturer;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

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
        IntStream.range(0, Storage.manufacturers.size())
                .filter(m -> Storage.manufacturers.get(m).getId().equals(manufacturer.getId()))
                .forEach(i -> Storage.manufacturers.set(i, manufacturer));
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.manufacturers.removeIf(m -> m.getId().equals(id));
    }

    @Override
    public List<Manufacturer> getAllManufactures() {
        return Storage.manufacturers;
    }
}
