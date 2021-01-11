package com.igorbochkor.service;

import com.igorbochkor.dao.ManufacturerDao;
import com.igorbochkor.lib.Inject;
import com.igorbochkor.lib.Service;
import com.igorbochkor.model.Manufacturer;
import java.util.List;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {
    @Inject
    private ManufacturerDao manufacturerDao;

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        return manufacturerDao.create(manufacturer);
    }

    @Override
    public Manufacturer get(Long id) {
        return manufacturerDao.get(id).orElseThrow(()
                -> new RuntimeException("Can't find manufacturer!"));
    }

    @Override
    public List<Manufacturer> getAll() {
        return manufacturerDao.getAllManufactures();
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        return manufacturerDao.update(manufacturer);
    }

    @Override
    public boolean delete(Long id) {
        return manufacturerDao.delete(id);
    }
}
