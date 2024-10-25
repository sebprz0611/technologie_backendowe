package com.capgemini.wsb.fitnesstracker.user.api;

import com.capgemini.wsb.fitnesstracker.user.internal.UserEmailDto;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user);
    void deleteUser(Long id);
    List<UserEmailDto> findUsersByEmailFragment(String emailFragment);
}