package com.electrolux.controller;

import com.electrolux.entity.Entry;
import com.electrolux.exception.ResourceNotFoundException;
import com.electrolux.services.EntryService;
import com.electrolux.utils.Status;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/log")
@Api(value = "REST provides function for work with entry for writing new status")
@RequiredArgsConstructor
public class EntryController {
    private final EntryService entryService;

    @GetMapping
    public List<Entry> getAllLogs() {
        return entryService.getAllEntries();
    }

    @GetMapping("/{id}")
    @ApiOperation("get entry by id")
    public ResponseEntity<Entry> getLogById(@PathVariable(value = "id") Long logId) throws ResourceNotFoundException {
        Entry entry = entryService.findById(logId);
        return ResponseEntity.ok().body(entry);
    }

    @GetMapping("/for_model/{id}")
    @ApiOperation("get entries in washing machine by id")
    public Set<Entry> getAllEntriesByWMId(@PathVariable(value = "id") Long wmId) throws ResourceNotFoundException {
        return entryService.findByWMId(wmId);
    }

    @GetMapping("/{firstDate}/between/{lastDate}")
    @ApiOperation("get entries in time period")
    public Set<Entry> getAllTimeBetween(
            @PathVariable(value = "firstDate") String firstDate,
            @PathVariable(value = "lastDate") String lastDate
    ) throws ResourceNotFoundException {
        return entryService.findByDataBetween(firstDate, lastDate);
    }

    @GetMapping("/getAll/{createdDate}")
    @ApiOperation("get entries on date create")
    public Set<Entry> getAllByCreateTame(
            @PathVariable(value = "createdDate") String createdDate) throws ResourceNotFoundException {
        return entryService.findAllByCreatedAt(createdDate);
    }

    @PutMapping("/createFor/{wmId}/andMode/{modeId}")
    @ApiOperation("add entry")
    public ResponseEntity<Status> addEntry(
            @PathVariable(value = "wmId") Long wmId,
            @PathVariable(value = "modeId") Long modeId
    ) throws ResourceNotFoundException {
        return ResponseEntity.ok(entryService.addEntry(wmId, modeId));
    }
}
