package com.igorbochkor.security;

import com.igorbochkor.exception.AuthenticationException;
import com.igorbochkor.model.Driver;

public interface AuthenticationService {
    Driver login(String login, String password) throws AuthenticationException;
}
