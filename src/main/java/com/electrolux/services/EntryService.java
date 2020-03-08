package com.electrolux.services;

import com.electrolux.entity.Entry;
import com.electrolux.entity.User;
import com.electrolux.entity.Model;
import com.electrolux.entity.WorkMode;
import com.electrolux.exception.ResourceNotFoundException;
import com.electrolux.repository.EntryRepository;
import com.electrolux.repository.ModelRepository;
import com.electrolux.repository.WorkModeRepository;
import com.electrolux.utils.Converter;
import com.electrolux.utils.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.*;

public interface EntryService {

    List<Entry> getAllEntries();

    Entry findById(@Nonnull long logId);

    Set<Entry> findByWMId(@Nonnull long wmId);

    /**
     * select all entries by time interval
     * @param first - date of left convention of between
     * @param last - date of right convention of between
     * @return if entries are found {@link Entry} ResourceNotFoundException
     */
    Set<Entry> findByDataBetween(@Nonnull String first, String last);

    /**
     * select all entries by time create
     * @param create - date of created entry
     * @return if entries are found {@link Entry} ResourceNotFoundException
     */
    Set<Entry> findAllByCreatedAt(@Nonnull String create);

    /**
     * add a new record for this WM by id of the washing machine
     * to do this, create a new record and bind it to the washing machine
     * @param wmId - id washing machine
     * @param modeId - id workMode
     * @return {@link Status}
     */
    Status addEntry(@Nonnull long wmId, long modeId);

}
