package com.electrolux.controller;

import com.electrolux.entity.WorkMode;
import com.electrolux.exception.ResourceNotFoundException;
import com.electrolux.services.WorkModeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/mode")
@Api(value = "REST provides function for work with working mode for washing machine")
@RequiredArgsConstructor
public class WorkModeController {
    private final WorkModeService modeService;

    @GetMapping
    @ApiOperation("get all modes")
    public List<WorkMode> getAllModes() {
        return modeService.getAllWorkModes();
    }

    @GetMapping("/{id}")
    @ApiOperation("get mode by id")
    public ResponseEntity<WorkMode> getModeById(@PathVariable(value = "id") Long modeId) throws ResourceNotFoundException {
        WorkMode workMode = modeService.findById(modeId);
        return ResponseEntity.ok().body(workMode);
    }

    @GetMapping("/search/{nameMode}")
    @ApiOperation("get mode by name")
    public ResponseEntity<WorkMode> getModeByNameMode(@PathVariable(value = "nameMode") String nameMode) throws ResourceNotFoundException {
        WorkMode workMode = modeService.findByNameMode(nameMode);
        return ResponseEntity.ok().body(workMode);
    }

    @GetMapping("/for_user/{id}")
    @ApiOperation("get all modes from user by id")
    public Set<WorkMode> getAllModesByUserId(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException {
        return modeService.findByUserId(userId);
    }
    
    @PostMapping("/{userId}")
    @ApiOperation("create mode")
    public WorkMode createMode(@PathVariable Long userId, @Valid @RequestBody WorkMode newMode) {
        return modeService.createWorkMode(userId, newMode);
    }

    @PutMapping("/{userId}/mode/{modeId}")
    @ApiOperation("update modes")
    public ResponseEntity<WorkMode> updateMode(
            @PathVariable(value = "userId") Long userId,
            @PathVariable(value = "modeId") Long modeId,
            @Valid @RequestBody WorkMode mode
    ) throws ResourceNotFoundException {
        WorkMode workMode = modeService.updateWorkMode(userId, modeId, mode);
        return ResponseEntity.ok(workMode);
    }

    @DeleteMapping("/{userId}/mode/{modeId}")
    @ApiOperation("delete modes")
    public ResponseEntity<Long> deleteMode(@PathVariable(value = "userId") Long userId, @PathVariable(value = "modeId") Long modeId) {
        modeService.deleteWorkMode(userId, modeId);
        return ResponseEntity.ok().body(modeId);
    }

}
