package com.electrolux.repository;

import com.electrolux.entity.WashingMachineModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserModeRepository extends JpaRepository<WashingMachineModel, Long>{

}
