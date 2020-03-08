package com.electrolux.controller;

import com.electrolux.entity.User;
import com.electrolux.entity.WorkMode;
import com.electrolux.exception.ResourceNotFoundException;
import com.electrolux.services.UserService;
import com.electrolux.services.WorkModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/mode")
@RequiredArgsConstructor
public class WorkModeController {
    private final WorkModelService modeService;

    // выбрать все существующие режимы
    @GetMapping
    public List<WorkMode> getAllModes() {
        return modeService.getAllWorkModes();
    }

    // выбрать режим по id
    @GetMapping("/{id}")
    public ResponseEntity<WorkMode> getModeById(@PathVariable(value = "id") Long modeId) throws ResourceNotFoundException {
        WorkMode workMode = modeService.findById(modeId);
        return ResponseEntity.ok().body(workMode);
    }

    // выбрать режим по nameMode
    @GetMapping("/search/{nameMode}")
    public ResponseEntity<WorkMode> getModeByNameMode(@PathVariable(value = "nameMode") String nameMode) throws ResourceNotFoundException {
        WorkMode workMode = modeService.findByNameMode(nameMode);
        return ResponseEntity.ok().body(workMode);
    }

    // выбрать все существующие режимы у данного пользователя
    @GetMapping("/for_user/{id}")
    public Set<WorkMode> getAllModesByUserId(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException {
        return modeService.findByUserId(userId);
    }
    
    // создать режим и привязать его к user
    @PostMapping("/{userId}")
    public WorkMode createMode(@PathVariable Long userId, @Valid @RequestBody WorkMode newMode) {
        return modeService.createWorkMode(userId, newMode);
    }

    // редактировать режим может только user который этот режим создал
    @PutMapping("/{userId}/mode/{modeId}")
    public ResponseEntity<WorkMode> updateMode(
            @PathVariable(value = "userId") Long userId,
            @PathVariable(value = "modeId") Long modeId,
            @Valid @RequestBody WorkMode mode
    ) throws ResourceNotFoundException {
        WorkMode workMode = modeService.updateWorkMode(userId, modeId, mode);
        return ResponseEntity.ok(workMode);
    }

    // удалить режим может только юзер который его создал
    @DeleteMapping("/{userId}/mode/{modeId}")
    public ResponseEntity<Long> deleteMode(@PathVariable(value = "userId") Long userId, @PathVariable(value = "modeId") Long modeId) {
        modeService.deleteWorkMode(userId, modeId);
        return ResponseEntity.ok().body(modeId);
    }

}
