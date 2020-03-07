package com.electrolux.repository;

import com.electrolux.entity.User;
import com.electrolux.entity.WM_Model;
import com.electrolux.entity.WorkMode;
import com.electrolux.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    User findByLogin(String login) throws ResourceNotFoundException;
}
