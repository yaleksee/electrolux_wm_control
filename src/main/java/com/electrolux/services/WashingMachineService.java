package com.electrolux.services;

import com.electrolux.entity.User;
import com.electrolux.entity.Model;
import com.electrolux.entity.WorkMode;
import com.electrolux.exception.ResourceAccessException;
import com.electrolux.exception.ResourceNotFoundException;
import com.electrolux.repository.UserRepository;
import com.electrolux.repository.WM_Repository;
import com.electrolux.repository.WorkModeRepository;
import com.electrolux.utils.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WashingMachineService {

    private final WM_Repository wm_repository;
    private final WorkModeRepository workModeRepository;
    private final UserRepository userRepository;

    // создать см и привязать ее к user
    // подгрузить в нее режим стрирки по умолчанию
    public Model createModel(long userId, Model newModel) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found for this id : " + userId));
        Model model = wm_repository.findByModelName(newModel.getModelName());
        if (model != null) {
            throw new ResourceAccessException("Model with name " + newModel.getModelName() + " already exist");
        }
        newModel.setUser(user);
        return wm_repository.save(newModel);
    }

    // редактировать см может только user, который этой машиной владеет. 
    // Сменить владельца не возможно.
    // Сменить срок гарантии невозможно.
    // Сменить число стирок не возможно.
    public Model updateModel(long userId, long modelId, Model externalModel) throws ResourceNotFoundException {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found for this id : " + userId));
        String modelName = externalModel.getModelName();
        Model model = wm_repository.findByModelName(modelName);
        if (model != null)
            throw new ResourceAccessException("model with name " + modelName + " already exists");
        Model internalModel = findById(modelId);
        if(!internalModel.getModelName().equals(externalModel.getModelName())){
            throw new ResourceAccessException("Change model name impossible");
        }
        if (findByUserId(userId).contains(internalModel)) {
            final Model internalMode = findById(modelId);
            internalMode.setMainsVoltage(externalModel.getMainsVoltage());
            internalMode.setHardnessWater(externalModel.getHardnessWater());
            internalMode.setVolume(externalModel.getVolume());
            internalMode.setHexCodeCollor(externalModel.getHexCodeCollor());
            internalMode.setIsDisplay(externalModel.getIsDisplay());
            return wm_repository.save(internalMode);
        }
        throw new ResourceNotFoundException("User with id : " + userId + " don't author of this mode");
    }

    // выбрать см по id
    public Model findById(long modelId) throws ResourceNotFoundException {
        return wm_repository.findById(modelId)
                .orElseThrow(() -> new ResourceNotFoundException("Model not found for this id : " + modelId));
    }

    // выбрать см по modelName
    public Model findByNameModel(String modelName) throws ResourceNotFoundException {
        Model model = wm_repository.findByModelName(modelName);
        if (model == null) throw new ResourceNotFoundException("model not found for this name : " + modelName);
        return model;
    }

    // выбрать все существующие см
    public List<Model> getAllWM_Models() {
        return wm_repository.findAll();
    }

    // выбрать все существующие см у данного пользователя
    @Transactional
    public Set<Model> findByUserId(long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found for this id : " + userId));
        Set<Model> models = wm_repository.findByUserId(userId);
        if (models == null) throw new ResourceNotFoundException("models not found for user with id: " + userId);
        return models;
    }

    // выбрать режимы которые установлены в стиральную машину по id стиральной машины
    public Set<WorkMode> getAllModesFromWM(long wmId) {
        Model model = findById(wmId);
        return model.getWorkModes();
    }

    // пользователь загружает в текущую модель режимы стрирки по id режимов
    // пользователь не имеет доступ к режимам которые ему не пренадлежат
    public Status putSomeModeIntoWM(long userId, long wmId, List<Long> arrModeId) {
        Model model = findById(wmId);
        userRepository.findById(userId);
        if (model.getUser().getId() != userId)
            throw new ResourceAccessException("User with id " + userId + " cannot put modes, because it isn't his wm");
        Set<WorkMode> modes = new HashSet<>();
        Set<WorkMode> workModes = workModeRepository.findByUserId(userId);
        for (long id : arrModeId) {
            WorkMode workMode = workModeRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("workMode not found for this id : " + id));
            if (workModes.contains(workMode)) {
                modes.add(workMode);
            }
        }

        model.setWorkModes(modes);
        wm_repository.save(model);
        return new Status("Putting success", HttpStatus.OK);
    }

    // пользователь загружает в текущую модель все свои режимы стрирки
    public Status putAllModeIntoWM(long userId, long wmId) {
        Model model = findById(wmId);
        userRepository.findById(userId);
        if (model.getUser().getId() != userId)
            throw new ResourceAccessException("User with id " + userId + " cannot put modes, because it isn't his wm");
        Set<WorkMode> workModes = workModeRepository.findByUserId(userId);

        model.setWorkModes(workModes);
        wm_repository.save(model);
        return new Status("Putting success", HttpStatus.OK);
    }

    // пользователь удаляет режимы стирки по их id кроме режима по умолчанию "default"
    public Status deleteSomeModeIntoWM(long userId, long wmId, List<Long> arrModeId) {
        Model model = findById(wmId);
        userRepository.findById(userId);
        if (model.getUser().getId() != userId)
            throw new ResourceAccessException("User with id " + userId + " cannot delete modes, because it isn't his wm");
        Set<WorkMode> modes = getAllModesFromWM(wmId);
        for (long id : arrModeId) {
            WorkMode workMode = workModeRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("workMode not found for this id : " + id));
            if (!workMode.getNameMode().equals("default")) {
                modes.remove(workMode);
            }
        }
        model.setWorkModes(modes);
        wm_repository.save(model);
        return new Status("Deleting success", HttpStatus.OK);
    }

    // пользователь удаляет все режимы стирки кроме режима по умолчанию "default"
    public Status deleteAllModeIntoWM(long userId, long wmId) {
        Model model = findById(wmId);
        userRepository.findById(userId);
        if (model.getUser().getId() != userId)
            throw new ResourceAccessException("User with id " + userId + " cannot delete modes, because it isn't his wm");
        Set<WorkMode> removedSet = getAllModesFromWM(wmId).stream()
                .filter(mode -> (mode.getNameMode().equals("default")))
                .collect(Collectors.toSet());
        model.setWorkModes(removedSet);
        wm_repository.save(model);
        return new Status("Deleting success", HttpStatus.OK);
    }

    // удалить см может только юзер который ее создал
    public void deleteWMModel(Long userId, Long modelId) throws ResourceNotFoundException {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found for this id : " + userId));
        Model internalModel = findById(modelId);
        if (findByUserId(userId).contains(internalModel)) {
            workModeRepository.deleteById(modelId);
        } else {
            throw new ResourceAccessException("User with id " + userId + " cannot delete mode with id " + modelId);
        }
    }
}
