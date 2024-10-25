package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;

    @Override
    public User createUser(final User user) {
        log.info("Creating User {}", user);
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserByNameAndSurname(final String firstName, final String lastName) {
        return userRepository.findAll().stream()
                .filter(user -> user.getFirstName().equalsIgnoreCase(firstName) &&
                        user.getLastName().equalsIgnoreCase(lastName))
                .findFirst();
    }

    @Override
    public Optional<User> getUserByBirthdate(final LocalDate birthdate) {
        return userRepository.findAll().stream()
                .filter(user -> user.getBirthdate().equals(birthdate))
                .findFirst();
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserEmailDto> findUsersByEmailFragment(String emailFragment) {
        List<User> users = userRepository.findByEmailContainingIgnoreCase(emailFragment);
        return users.stream()
                .map(user -> new UserEmailDto(user.getId(), user.getEmail()))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findUsersOlderThan(int age) {
        LocalDate cutoffDate = LocalDate.now().minusYears(age);
        return userRepository.findAll().stream()
                .filter(user -> user.getBirthdate().isBefore(cutoffDate))
                .collect(Collectors.toList());
    }

}