package com.electrolux.repository;

import com.electrolux.entity.WM_Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceStatusRepository extends JpaRepository<WM_Model, Long>{

}
