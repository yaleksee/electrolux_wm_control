package com.electrolux.repository;

import com.electrolux.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    User findByLogin(@Nonnull String login);
}
