package com.electrolux.repository;

import com.electrolux.entity.User;
import com.electrolux.entity.WorkMode;
import com.electrolux.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface WorkModeRepository extends JpaRepository<WorkMode, Long>{
    Set<WorkMode> findByUserId(@Nonnull Long userId);
    WorkMode findByNameMode(@Nonnull String nameMode);
}
