package com.electrolux.repository;

import com.electrolux.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.util.Set;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long>{
    Set<Model> findByUserId(@Nonnull Long userId);
    Model findByModelName(@Nonnull String modelName);
}
