package com.electrolux.controller;

import com.electrolux.entity.Model;
import com.electrolux.entity.WorkMode;
import com.electrolux.exception.ResourceNotFoundException;
import com.electrolux.services.WashingMachineService;
import com.electrolux.services.WorkModelService;
import com.electrolux.utils.ListModeWrapper;
import com.electrolux.utils.Status;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/model")
@Api(value = "REST provides function for work with model washing machine")
@RequiredArgsConstructor
public class ModelController {
    private final WorkModelService modeService;
    private final WashingMachineService washingMachineService;

    @GetMapping
    @ApiOperation("get models")
    public List<Model> getAllModels() {
        return washingMachineService.getAllWM_Models();
    }

    @GetMapping("/{id}")
    @ApiOperation("get models by id")
    public ResponseEntity<Model> getModelById(@PathVariable(value = "id") Long modelId) throws ResourceNotFoundException {
        Model model = washingMachineService.findById(modelId);
        return ResponseEntity.ok().body(model);
    }

    @GetMapping("/search/{nameModel}")
    @ApiOperation("get models by name model")
    public ResponseEntity<Model> getModelByNameMode(@PathVariable(value = "nameModel") String nameModel) throws ResourceNotFoundException {
        Model model = washingMachineService.findByNameModel(nameModel);
        return ResponseEntity.ok().body(model);
    }

    @GetMapping("/for_user/{id}")
    @ApiOperation("get models by user id")
    public Set<Model> getAllModelsByUserId(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException {
        return washingMachineService.findByUserId(userId);
    }

    @GetMapping("/allModesByWMId/{id}")
    @ApiOperation("get modes in washing machine by id")
    public Set<WorkMode> getAllModesByWMId(@PathVariable(value = "id") Long wmId) throws ResourceNotFoundException {
        return washingMachineService.getAllModesFromWM(wmId);
    }

    @PostMapping("/{userId}")
    @ApiOperation("create model")
    public Model createModel(@PathVariable Long userId, @Valid @RequestBody Model newModel) {
        return washingMachineService.createModel(userId, newModel);
    }

    @PutMapping("/{userId}/model/{modelId}")
    @ApiOperation("update model")
    public ResponseEntity<Model> updateModel(
            @PathVariable(value = "userId") Long userId,
            @PathVariable(value = "modelId") Long modelId,
            @Valid @RequestBody Model model
    ) throws ResourceNotFoundException {
        Model updateModel = washingMachineService.updateModel(userId, modelId, model);
        return ResponseEntity.ok(updateModel);
    }

    @PutMapping("/putSomeMode/{userId}/model/{modelId}")
    @ApiOperation("put list modes into model")
    public ResponseEntity<Status> putSomeModesIntoWM(
            @PathVariable(value = "userId") Long userId,
            @PathVariable(value = "modelId") Long modelId,
            @Valid @RequestBody ListModeWrapper listModes
    ) throws ResourceNotFoundException {
        return ResponseEntity.ok(washingMachineService.putSomeModeIntoWM(userId, modelId, listModes.getListModes()));
    }

    @PutMapping("/putAllMode/{userId}/model/{modelId}")
    @ApiOperation("put all user modes into model")
    public ResponseEntity<Status> putAllModesIntoWM(
            @PathVariable(value = "userId") Long userId,
            @PathVariable(value = "modelId") Long modelId
    ) throws ResourceNotFoundException {
        return ResponseEntity.ok(washingMachineService.putAllModeIntoWM(userId, modelId));
    }

    @PutMapping("/deleteSomeModes/{userId}/model/{modelId}")
    @ApiOperation("delete list modes from model")
    public ResponseEntity<Status> deleteSomeModesIntoWM(
            @PathVariable(value = "userId") Long userId,
            @PathVariable(value = "modelId") Long modelId,
            @Valid @RequestBody ListModeWrapper listModes
    ) throws ResourceNotFoundException {
        return ResponseEntity.ok(washingMachineService.deleteSomeModeIntoWM(userId, modelId, listModes.getListModes()));
    }

    @PutMapping("/deleteAllModes/{userId}/model/{modelId}")
    @ApiOperation("put modes bu id from model")
    public ResponseEntity<Status> deleteAllModesIntoWM(
            @PathVariable(value = "userId") Long userId,
            @PathVariable(value = "modelId") Long modelId
    ) throws ResourceNotFoundException {
        return ResponseEntity.ok(washingMachineService.deleteAllModeIntoWM(userId, modelId));
    }

    @DeleteMapping("/{userId}/model/{modelId}")
    @ApiOperation("delete model")
    public ResponseEntity<Long> deleteMode(
            @PathVariable(value = "userId") Long userId,
            @PathVariable(value = "modelId") Long modelId) {
        washingMachineService.deleteWMModel(userId, modelId);
        return ResponseEntity.ok().body(modelId);
    }

}
