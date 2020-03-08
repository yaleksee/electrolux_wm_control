package com.electrolux.repository;

import com.electrolux.entity.WM_Model;
import com.electrolux.entity.WorkMode;
import com.electrolux.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface WM_Repository extends JpaRepository<WM_Model, Long>{
    Set<WM_Model> findByUserId(Long userId);
    WM_Model findByModelName(String modelName);
}
