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

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User createdUser = userService.createUser(user);
        UserDto createdUserDto = userMapper.toDto(createdUser);
        return ResponseEntity.status(201).body(createdUserDto);
    }

}
