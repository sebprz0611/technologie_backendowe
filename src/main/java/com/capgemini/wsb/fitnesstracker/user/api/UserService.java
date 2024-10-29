package com.capgemini.wsb.fitnesstracker.user.api;

import com.capgemini.wsb.fitnesstracker.user.internal.UserEmailDto;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user);
    Optional<User> getUser(Long userId);
    void deleteUser(Long id);
    List<UserEmailDto> findUsersByEmailFragment(String emailFragment);
    User updateUser(Long userId, User updatedUser);
}