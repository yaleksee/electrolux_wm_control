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

import javax.annotation.Nonnull;
import java.util.List;

public interface UserService {

    User createUser(@Nonnull User newUser);

    User updateUser(@Nonnull Long userId, User externalUser);

    User findById(@Nonnull Long userId);

    User findByLogin(@Nonnull String login);

    List<User> getAllUsers();

    void deleteUser(@Nonnull Long userId);

}
