package com.electrolux.services;

import com.electrolux.entity.User;
import com.electrolux.entity.WorkMode;
import com.electrolux.exception.ResourceNotFoundException;
import com.electrolux.repository.UserRepository;
import com.electrolux.repository.WorkModeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final WorkModeRepository workModeRepository;

    // создать пользователя
    public User createUser(User newUser) {
        User user = userRepository.findByLogin(newUser.getLogin());
        if (user != null) {
            throw new ResourceNotFoundException("user with login : " + newUser.getLogin() + " already exist");
        }
        return userRepository.save(newUser);
    }

    // редактировать пользователя
    public User updateUser(Long userId, User externalUser) throws ResourceNotFoundException {
        final User internalUser = findById(userId);
        internalUser.setLogin(externalUser.getLogin());
        internalUser.setFirstName(externalUser.getFirstName());
        internalUser.setLastName(externalUser.getLastName());
        return userRepository.save(internalUser);
    }

    // выбрать пользователя по id
    public User findById(long userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found for this id : " + userId));
        return user;
    }

    // выбрать пользователя по login
    public User findByLogin(String login) throws ResourceNotFoundException {
        User user = userRepository.findByLogin(login);
        if (user == null) {
            throw new ResourceNotFoundException("user not found for this login : " + login);
        }
        return user;
    }

    // выбрать всех существующих пользователей
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // удалить пользователя
    public void deleteUser(Long userId) throws ResourceNotFoundException {
        if (userRepository.findById(userId) != null)
            userRepository.deleteById(userId);
    }

}
