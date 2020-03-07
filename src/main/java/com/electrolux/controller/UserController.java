package com.electrolux.controller;

import com.electrolux.entity.User;
import com.electrolux.exception.ResourceNotFoundException;
import com.electrolux.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // выбрать всех существующих пользователей
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // выбрать пользователя по id
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException {
        User user = userService.findById(userId);
        return ResponseEntity.ok().body(user);
    }

    //выбрать пользователя по login
    @GetMapping("/search/{login}")
    public ResponseEntity<User> getUserByLogin(@PathVariable(value = "login") String login) throws ResourceNotFoundException {
        User user = userService.findByLogin(login);
        return ResponseEntity.ok().body(user);
    }

    // создать пользователя
    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    // редактировать пользователя
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @Valid @RequestBody User userDetails) throws ResourceNotFoundException {
        User updatedUser = userService.updateUser(userId, userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    // удалить пользователя
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteUser(@PathVariable(value = "id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().body(id);
    }

}
