package com.igorbochkor.service;

import com.igorbochkor.model.Manufacturer;
import java.util.List;

public interface ManufacturerService {
    Manufacturer create(Manufacturer manufacturer);

    Manufacturer get(Long id);

    List<Manufacturer> getAll();

    Manufacturer update(Manufacturer manufacturer);

    boolean delete(Long id);
}
