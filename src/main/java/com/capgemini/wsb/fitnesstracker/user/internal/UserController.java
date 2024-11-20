package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor

class UserController {

    private final UserServiceImpl userService;
    private final UserMapper userMapper;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                          .stream()
                          .map(userMapper::toDto)
                          .toList();
    }

    @GetMapping ("/simple")
    public List<UserSimpleDto> getAllBasicInformationAboutUser() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toSimpleDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUser(id);
        return user.map(userMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/email")
    public ResponseEntity<List<UserDto>> getUserByEmail(@RequestParam String email) {
        Optional<User> user = userService.getUserByEmail(email);
        if (user.isPresent()) {
            return ResponseEntity.ok(List.of(userMapper.toDto(user.get())));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<UserDto> getUserDetails(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) LocalDate birthdate,
            @RequestParam(required = false) String email) {
        Optional<User> user;
        if (id != null) {
            user = userService.getUser(id);
        } else if (firstName != null && lastName != null) {
            user = userService.getUserByNameAndSurname(firstName, lastName);
        } else if (birthdate != null) {
            user = userService.getUserByBirthdate(birthdate);
        } else if (email != null) {
            user = userService.getUserByEmail(email);
        } else {
            return ResponseEntity.badRequest().build();
        }
        return user.map(userMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search/email")
    public ResponseEntity<List<UserEmailDto>> getUsersByEmailFragment(@RequestParam String emailFragment) {
        List<UserEmailDto> users = userService.findUsersByEmailFragment(emailFragment);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/older-than")
    public ResponseEntity<List<UserDto>> getUsersOlderThan(@RequestParam int age) {
        LocalDate thresholdDate = LocalDate.now().minusYears(age);
        List<User> users = userService.findUsersOlderThan(thresholdDate);
        List<UserDto> userDtos = users.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping("/older/{birthdate}")
    public ResponseEntity<List<UserDto>> getUsersOlderThan(@PathVariable LocalDate birthdate) {
        List<User> users = userService.findUsersOlderThan(birthdate);
        List<UserDto> userDtos = users.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDtos);
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User createdUser = userService.createUser(user);
        UserDto createdUserDto = userMapper.toDto(createdUser);
        return ResponseEntity.status(201).body(createdUserDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.getUser(id).isPresent()) {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build(); // Zwraca 204 No Content jeśli usunięcie się powiedzie
        } else {
            return ResponseEntity.notFound().build(); // Zwraca 404 Not Found, jeśli użytkownik o podanym ID nie istnieje
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long userId, @RequestBody UserDto updatedUserDto) {
        User updatedUser = userMapper.toEntity(updatedUserDto);
        User user = userService.updateUser(userId, updatedUser);
        UserDto userDto = userMapper.toDto(user);
        return ResponseEntity.ok(userDto);
    }

}
