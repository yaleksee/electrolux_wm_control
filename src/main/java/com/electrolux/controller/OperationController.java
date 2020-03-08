package com.electrolux.controller;

import com.electrolux.exception.ResourceNotFoundException;
import com.electrolux.utils.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/operation")
@RequiredArgsConstructor
public class OperationController {


    // добавить новую запись для данной см по id стриральной машины
    // для этого создать новую запись и привязать ее к стиральной машине
//    @PutMapping("/startWashingOn/{wmId}/changeMode/{modeId}")
//    public ResponseEntity<Status> addEntry(
//            @PathVariable(value = "wmId") Long wmId,
//            @PathVariable(value = "modeId") Long modeId
//    ) throws ResourceNotFoundException {
//        return ResponseEntity.ok(entryService.addEntry(wmId, modeId));
//    }
}
