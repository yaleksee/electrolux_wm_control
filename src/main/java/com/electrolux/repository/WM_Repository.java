package com.electrolux.repository;

import com.electrolux.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface WM_Repository extends JpaRepository<Model, Long>{
    Set<Model> findByUserId(Long userId);
    Model findByModelName(String modelName);
}
