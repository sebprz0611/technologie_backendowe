package com.capgemini.wsb.fitnesstracker.user.api;

import com.capgemini.wsb.fitnesstracker.exception.api.NotFoundException;

@SuppressWarnings("squid:S110")
public class UserNotFoundException extends NotFoundException {
    private UserNotFoundException(String message) {
        super(message);
    }
    public UserNotFoundException(Long id) {
        this("User with ID=%s was not found".formatted(id));
    }
}
