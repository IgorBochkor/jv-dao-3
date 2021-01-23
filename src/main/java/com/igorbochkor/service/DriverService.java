package com.igorbochkor.service;

import com.igorbochkor.model.Driver;
import java.util.Optional;

public interface DriverService extends GenericService<Driver, Long> {
    Optional<Driver> findByLogin(String login);
}
