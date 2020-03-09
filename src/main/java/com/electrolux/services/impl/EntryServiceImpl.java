package com.electrolux.services.impl;

import com.electrolux.entity.Entry;
import com.electrolux.entity.Model;
import com.electrolux.entity.User;
import com.electrolux.entity.WorkMode;
import com.electrolux.exception.ResourceNotFoundException;
import com.electrolux.repository.EntryRepository;
import com.electrolux.repository.ModelRepository;
import com.electrolux.repository.WorkModeRepository;
import com.electrolux.services.EntryService;
import com.electrolux.utils.Converter;
import com.electrolux.utils.Status;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EntryServiceImpl implements EntryService {
    private static final Logger LOG = LoggerFactory.getLogger(EntryServiceImpl.class);

    private final EntryRepository entryRepository;
    private final ModelRepository model_repository;
    private final WorkModeRepository workModeRepository;

    @Override
    public List<Entry> getAllEntries() {
        return entryRepository.findAll();
    }

    @Override
    public Entry findById(@Nonnull Long logId) throws ResourceNotFoundException {
        return entryRepository.findById(logId)
                .orElseThrow(() -> new ResourceNotFoundException("Entry not found for this id : " + logId));
    }

    @Override
    public Set<Entry> findByWMId(@Nonnull Long wmId) {
        model_repository.findById(wmId)
                .orElseThrow(() -> new ResourceNotFoundException("washing machine not found for this id : " + wmId));
        final Set<Entry> entries = entryRepository.findByModelId(wmId);
        if (entries == null) {
            LOG.error("Entry not found for washing machine with id: " + wmId);
            throw new ResourceNotFoundException("Entry not found for washing machine with id: " + wmId);
        }
        return entries;
    }

    @Override
    public Set<Entry> findByDataBetween(@Nonnull String first, String last) {
        final Date firstDate = Converter.converter(first);
        final Date lastDate = Converter.converter(last);
        final Set<Entry> entries = entryRepository.findAllByCurrentlyDateBetween(firstDate, lastDate);
        if (entries.size() == 0) {
            LOG.error("Entries not found between this dates: " + firstDate + " and " + lastDate);
            throw new ResourceNotFoundException("Entries not found between this dates: " + firstDate + " and " + lastDate);
        }
        return entries;
    }

    @Override
    public Set<Entry> findAllByCreatedAt(@Nonnull String create) {
        final Date createDate = Converter.converter(create);
        final Set<Entry> entries = entryRepository.findAllByCreatedAt(createDate);
        if (entries.size() == 0) {
            LOG.error("Entries not found on date: " + createDate);
            throw new ResourceNotFoundException("Entries not found on date: " + createDate);
        }
        return entries;
    }

    @Override
    @Transactional
    public Status addEntry(@Nonnull Long wmId, Long modeId) {
        final Model model = model_repository.findById(wmId)
                .orElseThrow(() -> new ResourceNotFoundException("washing machine not found for this id : " + wmId));
        final WorkMode workMode = workModeRepository.findById(modeId)
                .orElseThrow(() -> new ResourceNotFoundException("workMode not found for this id : " + modeId));
        final User user = model.getUser();
        final Set<WorkMode> workModes = model.getWorkModes();
        Entry entry = new Entry();
        if (workModes == null) {
            LOG.error("workModes not found for the washing machine with id: " + model.getId());
            throw new ResourceNotFoundException("workModes not found for the washing machine with id: " + model.getId());
        }
        if (workModes.contains(workMode)) {
            entry.setModel(model);
            entry.setCurrentlyDate(new Date());
            String log = "User with login: " + user.getLogin()
                    + " | run the washing mode with following futures: " + workMode.toString()
                    + " | on washing machine with a following futures: " + model.toString();
            entry.setLog(log);
            entryRepository.save(entry);
        } else {
            LOG.error("workMode with id: " + modeId + " not found in the washing machine with id: " + wmId);
            throw new ResourceNotFoundException("workMode with id: " + modeId + " not found in the washing machine with id: " + wmId);
        }
        List<String> list = new ArrayList<>();
        list.add(entry.getLog());
        list.add("The entry was successfully save!");
        return new Status(list, HttpStatus.OK);
    }

}
