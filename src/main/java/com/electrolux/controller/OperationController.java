package com.electrolux.controller;

import com.electrolux.exception.ResourceNotFoundException;
import com.electrolux.facade.WashingMachineFacade;
import com.electrolux.utils.Status;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/operation")
@Api(value = "Start wash washing machine wish changed mode")
@RequiredArgsConstructor
public class OperationController {
    private final WashingMachineFacade washingMachineFacade;

    @GetMapping("/run/{wmId}/withMode/{modeId}")
    @ApiOperation("run wash washing machine with mode")
    public ResponseEntity<Status> startWash(
            @PathVariable(value = "wmId") Long wmId,
            @PathVariable(value = "modeId") Long modeId
    ) throws ResourceNotFoundException {
        return ResponseEntity.ok(washingMachineFacade.startWash(wmId, modeId));
    }
}
