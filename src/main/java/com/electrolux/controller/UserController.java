package com.electrolux.controller;

import com.electrolux.entity.User;
import com.electrolux.exception.ResourceNotFoundException;
import com.electrolux.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@Api(value = "REST provides function for work with user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    @ApiOperation("get all users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @ApiOperation("get user by id")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException {
        User user = userService.findById(userId);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/search/{login}")
    @ApiOperation("get user by login")
    public ResponseEntity<User> getUserByLogin(@PathVariable(value = "login") String login) throws ResourceNotFoundException {
        User user = userService.findByLogin(login);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping
    @ApiOperation("create user")
    public User createUser(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/{userId}")
    @ApiOperation("update user")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @Valid @RequestBody User userDetails) throws ResourceNotFoundException {
        User updatedUser = userService.updateUser(userId, userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("delete user")
    public ResponseEntity<Long> deleteUser(@PathVariable(value = "id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().body(id);
    }

}
