package com.electrolux.services;

import com.electrolux.entity.Entry;
import com.electrolux.entity.User;
import com.electrolux.entity.Model;
import com.electrolux.entity.WorkMode;
import com.electrolux.exception.ResourceNotFoundException;
import com.electrolux.repository.EntryRepository;
import com.electrolux.repository.WM_Repository;
import com.electrolux.repository.WorkModeRepository;
import com.electrolux.utils.Converter;
import com.electrolux.utils.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class EntryService {

    private final EntryRepository entryRepository;
    private final WM_Repository wm_repository;
    private final WorkModeRepository workModeRepository;

    // выбрать все существующие записи
    public List<Entry> getAllLogs() {
        return entryRepository.findAll();
    }

    // выбрать запись по id
    public Entry findById(long logId) throws ResourceNotFoundException {
        return entryRepository.findById(logId)
                .orElseThrow(() -> new ResourceNotFoundException("ApplianceLog not found for this id : " + logId));
    }

    // выбрать все записи по id стриральной машины
    public Set<Entry> findByWMId(long wmId) {
        wm_repository.findById(wmId)
                .orElseThrow(() -> new ResourceNotFoundException("washing machine not found for this id : " + wmId));
        Set<Entry> logs = entryRepository.findByModelId(wmId);
        if (logs == null) throw new ResourceNotFoundException("logs not found for washing machine with id: " + wmId);
        return logs;
    }

    // выбрать все записи по временному интервалу
    public Set<Entry> findByDataBetween(String first, String last) {
        Date firstDate = Converter.converter(first);
        Date lastDate = Converter.converter(last);
        Set<Entry> entries = entryRepository.findAllByCurrentlyDateBetween(firstDate, lastDate);
        if (entries.size() == 0)
            throw new ResourceNotFoundException("logs not found between this dates: " + firstDate + " and " + lastDate);
        return entries;
    }

    // выбрать все записи по времени создания
    public Set<Entry> findAllByCreatedAt(String create) {
        Date createDate = Converter.converter(create);
        Set<Entry> entries = entryRepository.findAllByCreatedAt(createDate);
        if (entries.size() == 0)
            throw new ResourceNotFoundException("logs not found on date: " + createDate);
        return entries;
    }

    // добавить новую запись для данной см по id стриральной машины
    // для этого создать новую запись и привязать ее к стиральной машине
    public Status addEntry(long wmId, long modeId) {
        final Model model = wm_repository.findById(wmId)
                .orElseThrow(() -> new ResourceNotFoundException("washing machine not found for this id : " + wmId));
        final WorkMode workMode = workModeRepository.findById(modeId)
                .orElseThrow(() -> new ResourceNotFoundException("workMode not found for this id : " + modeId));
        final User user = model.getUser();
        Set<WorkMode> workModes = model.getWorkModes();
        Entry entry = new Entry();
        if (workModes == null)
            throw new ResourceNotFoundException("workModes not found for the washing machine with id: " + model.getId());
        if (workModes.contains(workMode)) {
            entry.setModel(model);
            entry.setCurrentlyDate(new Date());
            String log = "User with login: " + user.getLogin()
                    + " | run the washing mode with following futures: " + workMode.toString()
                    + " | on washing machine with a following futures: " + model.toString();
            entry.setLog(log);
            entryRepository.save(entry);
        } else {
            throw new ResourceNotFoundException("workMode with id: " + modeId + " not found in the washing machine with id: " + wmId);
        }
        return new Status(entry.getLog(), HttpStatus.OK);
    }

}
