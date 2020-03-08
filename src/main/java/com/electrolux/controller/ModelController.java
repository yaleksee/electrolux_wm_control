package com.electrolux.controller;

import com.electrolux.entity.Model;
import com.electrolux.entity.WorkMode;
import com.electrolux.exception.ResourceNotFoundException;
import com.electrolux.services.WashingMachineService;
import com.electrolux.services.WorkModelService;
import com.electrolux.utils.ListModeWrapper;
import com.electrolux.utils.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/model")
@RequiredArgsConstructor
public class ModelController {
    private final WorkModelService modeService;
    private final WashingMachineService washingMachineService;

    // выбрать все существующие см
    @GetMapping
    public List<Model> getAllModels() {
        return washingMachineService.getAllWM_Models();
    }

    // выбрать см по id
    @GetMapping("/{id}")
    public ResponseEntity<Model> getModelById(@PathVariable(value = "id") Long modelId) throws ResourceNotFoundException {
        Model model = washingMachineService.findById(modelId);
        return ResponseEntity.ok().body(model);
    }

    // выбрать см по nameModel
    @GetMapping("/search/{nameModel}")
    public ResponseEntity<Model> getModelByNameMode(@PathVariable(value = "nameModel") String nameModel) throws ResourceNotFoundException {
        Model model = washingMachineService.findByNameModel(nameModel);
        return ResponseEntity.ok().body(model);
    }

    // выбрать все существующие см у данного пользователя
    @GetMapping("/for_user/{id}")
    public Set<Model> getAllModelsByUserId(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException {
        return washingMachineService.findByUserId(userId);
    }

    // выбрать режимы которые установлены в стиральную машину по id стиральной машины
    @GetMapping("/allModesByWMId/{id}")
    public Set<WorkMode> getAllModesByWMId(@PathVariable(value = "id") Long wmId) throws ResourceNotFoundException {
        return washingMachineService.getAllModesFromWM(wmId);
    }

    // создать см и привязать ее к user
    @PostMapping("/{userId}")
    public Model createModel(@PathVariable Long userId, @Valid @RequestBody Model newModel) {
        return washingMachineService.createModel(userId, newModel);
    }

    // редактировать см может только user, который этой машиной владеет.
    // Сменить владельца не возможно.
    // Сменить срок гарантии невозможно.
    @PutMapping("/{userId}/model/{modelId}")
    public ResponseEntity<Model> updateModel(
            @PathVariable(value = "userId") Long userId,
            @PathVariable(value = "modelId") Long modelId,
            @Valid @RequestBody Model model
    ) throws ResourceNotFoundException {
        Model updateModel = washingMachineService.updateModel(userId, modelId, model);
        return ResponseEntity.ok(updateModel);
    }

    // пользователь загружает в текущую модель некоторые режимы стрирки по id режимов
    // пользователь не имеет доступ к режимам которые ему не пренадлежат
    @PutMapping("/putSomeMode/{userId}/model/{modelId}")
    public ResponseEntity<Status> putSomeModesIntoWM(
            @PathVariable(value = "userId") Long userId,
            @PathVariable(value = "modelId") Long modelId,
            @Valid @RequestBody ListModeWrapper listModes
    ) throws ResourceNotFoundException {
        return ResponseEntity.ok(washingMachineService.putSomeModeIntoWM(userId, modelId, listModes.getListModes()));
    }

    // пользователь загружает в текущую модель все свои режимы стрирки
    @PutMapping("/putAllMode/{userId}/model/{modelId}")
    public ResponseEntity<Status> putAllModesIntoWM(
            @PathVariable(value = "userId") Long userId,
            @PathVariable(value = "modelId") Long modelId
    ) throws ResourceNotFoundException {
        return ResponseEntity.ok(washingMachineService.putAllModeIntoWM(userId, modelId));
    }

    // пользователь удаляет режимы стирки по их id кроме режима по умолчанию "default"
    @PutMapping("/deleteSomeModes/{userId}/model/{modelId}")
    public ResponseEntity<Status> deleteSomeModesIntoWM(
            @PathVariable(value = "userId") Long userId,
            @PathVariable(value = "modelId") Long modelId,
            @Valid @RequestBody ListModeWrapper listModes
    ) throws ResourceNotFoundException {
        return ResponseEntity.ok(washingMachineService.deleteSomeModeIntoWM(userId, modelId, listModes.getListModes()));
    }

    // пользователь удаляет все режимы стирки кроме режима по умолчанию "default"
    @PutMapping("/deleteAllModes/{userId}/model/{modelId}")
    public ResponseEntity<Status> deleteAllModesIntoWM(
            @PathVariable(value = "userId") Long userId,
            @PathVariable(value = "modelId") Long modelId
    ) throws ResourceNotFoundException {
        return ResponseEntity.ok(washingMachineService.deleteAllModeIntoWM(userId, modelId));
    }

    // удалить см может только юзер который эту см создал
    @DeleteMapping("/{userId}/model/{modelId}")
    public ResponseEntity<Long> deleteMode(
            @PathVariable(value = "userId") Long userId,
            @PathVariable(value = "modelId") Long modelId) {
        washingMachineService.deleteWMModel(userId, modelId);
        return ResponseEntity.ok().body(modelId);
    }

}
