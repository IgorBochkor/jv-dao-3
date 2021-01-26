package com.igorbochkor.security;

import com.igorbochkor.exception.AuthenticationException;
import com.igorbochkor.lib.Inject;
import com.igorbochkor.lib.Service;
import com.igorbochkor.model.Driver;
import com.igorbochkor.service.DriverService;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private DriverService driverService;

    @Override
    public Driver login(String login, String password) throws AuthenticationException {
        Optional<Driver> driverFromDB = driverService.findByLogin(login);
        if (driverFromDB.isPresent() && driverFromDB.get().getPassword().equals(password)) {
            return driverFromDB.get();
        }
        throw new AuthenticationException("Incorrect login or password");
    }
}
