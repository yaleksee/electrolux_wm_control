package com.electrolux.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.electrolux.entity.WashingMachineModel;

@Repository
public interface WashingMachineModelRepository extends JpaRepository<WashingMachineModel, Long>{

}
