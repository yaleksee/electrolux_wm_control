package com.electrolux.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.electrolux.entity.WM_Model;

@Repository
public interface WashingMachineModelRepository extends JpaRepository<WM_Model, Long>{

}
