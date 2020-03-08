package com.electrolux.repository;

import com.electrolux.entity.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import javax.annotation.Nonnull;

import java.util.Date;
import java.util.Set;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long>{
    Set<Entry> findByModelId(@Nonnull Long wmId);
    Set<Entry> findAllByCurrentlyDateBetween(@Nonnull Date firstDate, Date lastDate);
    Set<Entry> findAllByCreatedAt(@Nonnull Date create);
}
