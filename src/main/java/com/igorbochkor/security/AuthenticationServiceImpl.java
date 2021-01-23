package com.igorbochkor.security;

import com.igorbochkor.exception.AuthenticationException;
import com.igorbochkor.lib.Inject;
import com.igorbochkor.lib.Service;
import com.igorbochkor.model.Driver;
import com.igorbochkor.service.DriverService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private DriverService driverService;

    @Override
    public Driver login(String login, String password) throws AuthenticationException {
        Driver driverFromDB = driverService.findByLogin(login).orElseThrow(() ->
                new AuthenticationException("Incorrect login or password"));
        if (driverFromDB.getPassword().equals(password)) {
            return driverFromDB;
        }
        throw new AuthenticationException("Incorrect login or password");
    }
}
