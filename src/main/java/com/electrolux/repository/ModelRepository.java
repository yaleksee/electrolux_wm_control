package com.electrolux.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.electrolux.model.Model;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long>{

}
