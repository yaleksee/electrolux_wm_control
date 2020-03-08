package com.electrolux.services.impl;

import com.electrolux.entity.User;
import com.electrolux.exception.ResourceNotFoundException;
import com.electrolux.repository.UserRepository;
import com.electrolux.repository.WorkModeRepository;
import com.electrolux.services.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final WorkModeRepository workModeRepository;

    @Override
    @Transactional
    public User createUser(User newUser) {
        final User user = userRepository.findByLogin(newUser.getLogin());
        if (user != null) {
            LOG.error("user with login : " + newUser.getLogin() + " already exist");
            throw new ResourceNotFoundException("user with login : " + newUser.getLogin() + " already exist");
        }
        return userRepository.save(newUser);
    }

    @Override
    @Transactional
    public User updateUser(Long userId, User externalUser) throws ResourceNotFoundException {
        User internalUser = findById(userId);
        internalUser.setLogin(externalUser.getLogin());
        internalUser.setFirstName(externalUser.getFirstName());
        internalUser.setLastName(externalUser.getLastName());
        return userRepository.save(internalUser);
    }

    @Override
    public User findById(long userId) throws ResourceNotFoundException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found for this id : " + userId));
    }

    @Override
    public User findByLogin(String login) throws ResourceNotFoundException {
        final User user = userRepository.findByLogin(login);
        if (user == null) {
            LOG.error("user not found for this login : " + login);
            throw new ResourceNotFoundException("user not found for this login : " + login);
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) throws ResourceNotFoundException {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found for this id : " + userId));
        userRepository.deleteById(userId);
    }

}
