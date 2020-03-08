package com.electrolux.repository;

import com.electrolux.entity.User;
import com.electrolux.entity.WorkMode;
import com.electrolux.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface WorkModeRepository extends JpaRepository<WorkMode, Long>{
    Set<WorkMode> findByUserId(Long userId);
    WorkMode findByNameMode(String nameMode);
}
