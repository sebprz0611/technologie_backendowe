package com.capgemini.wsb.fitnesstracker.user.api;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserProvider {
    Optional<User> getUser(Long userId);
    Optional<User> getUserByEmail(String email);
    List<User> findAllUsers();
    Optional<User> getUserByNameAndSurname(String firstName, String lastName);
    Optional<User> getUserByBirthdate(LocalDate birthdate);
    Optional<User> getUserByBirthdateBefore(LocalDate birthdate);
}
