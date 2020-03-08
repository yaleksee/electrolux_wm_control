package com.electrolux.controller;

import com.electrolux.entity.Entry;
import com.electrolux.exception.ResourceNotFoundException;
import com.electrolux.services.EntryService;
import com.electrolux.utils.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/log")
@RequiredArgsConstructor
public class EntryController {
    private final EntryService entryService;

    // выбрать все существующие записи
    @GetMapping
    public List<Entry> getAllLogs() {
        return entryService.getAllLogs();
    }

    // выбрать запись по id
    @GetMapping("/{id}")
    public ResponseEntity<Entry> getLogById(@PathVariable(value = "id") Long logId) throws ResourceNotFoundException {
        Entry entry = entryService.findById(logId);
        return ResponseEntity.ok().body(entry);
    }

    // выбрать все записи по id стриральной машины
    @GetMapping("/for_model/{id}")
    public Set<Entry> getAllLogsByWMId(@PathVariable(value = "id") Long wmId) throws ResourceNotFoundException {
        return entryService.findByWMId(wmId);
    }

    // выбрать все записи по временному интервалу
    @GetMapping("/{firstDate}/between/{lastDate}")
    public Set<Entry> getAllTimeBetween(
            @PathVariable(value = "firstDate") String firstDate,
            @PathVariable(value = "lastDate") String lastDate
    ) throws ResourceNotFoundException {
        return entryService.findByDataBetween(firstDate, lastDate);
    }

    // выбрать все записи по времени создания
    // выбрать все записи по временному интервалу
    @GetMapping("/getAll/{createdDate}")
    public Set<Entry> getAllByCreateTame(
            @PathVariable(value = "createdDate") String createdDate) throws ResourceNotFoundException {
        return entryService.findAllByCreatedAt(createdDate);
    }

    // добавить новую запись для данной см по id стриральной машины
    // для этого создать новую запись и привязать ее к стиральной машине
    @PutMapping("/createFor/{wmId}/andMode/{modeId}")
    public ResponseEntity<Status> addEntry(
            @PathVariable(value = "wmId") Long wmId,
            @PathVariable(value = "modeId") Long modeId
    ) throws ResourceNotFoundException {
        return ResponseEntity.ok(entryService.addEntry(wmId, modeId));
    }
}
