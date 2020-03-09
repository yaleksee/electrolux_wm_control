package com.electrolux.services.impl;

import com.electrolux.entity.Model;
import com.electrolux.entity.User;
import com.electrolux.entity.WorkMode;
import com.electrolux.exception.ResourceAccessException;
import com.electrolux.exception.ResourceNotFoundException;
import com.electrolux.repository.ModelRepository;
import com.electrolux.repository.UserRepository;
import com.electrolux.repository.WorkModeRepository;
import com.electrolux.services.WashingMachineService;
import com.electrolux.utils.Status;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WashingMachineServiceImpl implements WashingMachineService {
    private static final Logger LOG = LoggerFactory.getLogger(WashingMachineService.class);

    private final ModelRepository model_repository;
    private final WorkModeRepository workModeRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Model createModel(@Nonnull Long userId, Model newModel) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found for this id : " + userId));
        final Model model = model_repository.findByModelName(newModel.getModelName());
        if (model != null) {
            LOG.error("Model with name " + newModel.getModelName() + " already exist");
            throw new ResourceAccessException("Model with name " + newModel.getModelName() + " already exist");
        }
        newModel.setUser(user);
        WorkMode defaultWorkMode = workModeRepository.findByNameMode("default");
        Set<WorkMode> workModes = new HashSet<>();
        workModes.add(defaultWorkMode);
        newModel.setWorkModes(workModes);
        return model_repository.save(newModel);
    }

    @Override
    @Transactional
    public Model updateModel(@Nonnull Long userId, Long modelId, Model externalModel) throws ResourceNotFoundException {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found for this id : " + userId));
        final Model internalModel = findById(modelId);
        if (internalModel.getWarrantyExpirationDate() != externalModel.getWarrantyExpirationDate()) {
            LOG.error("Change warranty period are not possible");
            throw new ResourceAccessException("Change warranty period are not possible");
        }
        if (findByUserId(userId).contains(internalModel)) {
            Model internalMode = findById(modelId);
            internalMode.setMainsVoltage(externalModel.getMainsVoltage());
            internalMode.setHardnessWater(externalModel.getHardnessWater());
            internalMode.setVolume(externalModel.getVolume());
            internalMode.setHexCodeCollor(externalModel.getHexCodeCollor());
            internalMode.setIsDisplay(externalModel.getIsDisplay());
            internalMode.setWorkModes(externalModel.getWorkModes());
            return model_repository.save(internalMode);
        }
        LOG.error("User with id : " + userId + " don't author of this mode");
        throw new ResourceNotFoundException("User with id : " + userId + " don't author of this mode");
    }

    @Override
    public Model findById(@Nonnull Long modelId) throws ResourceNotFoundException {
        return model_repository.findById(modelId)
                .orElseThrow(() -> new ResourceNotFoundException("Model not found for this id : " + modelId));
    }

    @Override
    public Model findByNameModel(@Nonnull String modelName) throws ResourceNotFoundException {
        final Model model = model_repository.findByModelName(modelName);
        if (model == null) {
            LOG.error("model not found for this name : " + modelName);
            throw new ResourceNotFoundException("model not found for this name : " + modelName);
        }
        return model;
    }

    @Override
    public List<Model> getAllWM_Models() {
        return model_repository.findAll();
    }

    @Override
    @Transactional
    public Set<Model> findByUserId(@Nonnull Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found for this id : " + userId));
        final Set<Model> models = model_repository.findByUserId(userId);
        if (models.size() == 0) {
            LOG.error("models not found for user with id: " + userId);
            throw new ResourceNotFoundException("models not found for user with id: " + userId);
        }
        return models;
    }

    @Override
    public Set<WorkMode> getAllModesFromWM(@Nonnull Long wmId) {
        Model model = findById(wmId);
        return model.getWorkModes();
    }

    @Override
    @Transactional
    public Status putSomeModeIntoWM(@Nonnull Long userId, Long wmId, List<Long> arrModeId) {
        final Model model = findById(wmId);
        userRepository.findById(userId);
        if (model.getUser().getId() != userId) {
            LOG.error("User with id " + userId + " cannot put modes, because it isn't his wm");
            throw new ResourceAccessException("User with id " + userId + " cannot put modes, because it isn't his wm");
        }
        final Set<WorkMode> modes = new HashSet<>();
        final Set<WorkMode> workModes = workModeRepository.findByUserId(userId);
        for (long id : arrModeId) {
            WorkMode workMode = workModeRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("workMode not found for this id : " + id));
            if (workModes.contains(workMode)) {
                modes.add(workMode);
            }
        }

        model.setWorkModes(modes);
        model_repository.save(model);
        List<String> list = new ArrayList<>();
        list.add("Putting success!");
        return new Status(list, HttpStatus.OK);
    }

    @Override
    @Transactional
    public Status putAllModeIntoWM(@Nonnull Long userId, Long wmId) {
        final Model model = findById(wmId);
        userRepository.findById(userId);
        if (model.getUser().getId() != userId) {
            LOG.error("User with id " + userId + " cannot put modes, because it isn't his wm");
            throw new ResourceAccessException("User with id " + userId + " cannot put modes, because it isn't his wm");
        }
        final Set<WorkMode> workModes = workModeRepository.findByUserId(userId);

        model.setWorkModes(workModes);
        model_repository.save(model);
        List<String> list = new ArrayList<>();
        list.add("Putting success!");
        return new Status(list, HttpStatus.OK);
    }

    @Override
    @Transactional
    public Status deleteSomeModeIntoWM(@Nonnull Long userId, Long wmId, List<Long> arrModeId) {
        final Model model = findById(wmId);
        userRepository.findById(userId);
        if (model.getUser().getId() != userId) {
            LOG.error("User with id " + userId + " cannot delete modes, because it isn't his wm");
            throw new ResourceAccessException("User with id " + userId + " cannot delete modes, because it isn't his wm");
        }
        final Set<WorkMode> modes = getAllModesFromWM(wmId);
        for (long id : arrModeId) {
            WorkMode workMode = workModeRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("workMode not found for this id : " + id));
            if (!workMode.getNameMode().equals("default")) {
                modes.remove(workMode);
            }
        }
        model.setWorkModes(modes);
        model_repository.save(model);
        List<String> list = new ArrayList<>();
        list.add("Deleting success!");
        return new Status(list, HttpStatus.OK);
    }

    @Override
    @Transactional
    public Status deleteAllModeIntoWM(@Nonnull Long userId, Long wmId) {
        final Model model = findById(wmId);
        userRepository.findById(userId);
        if (model.getUser().getId() != userId) {
            LOG.error("User with id " + userId + " cannot delete modes, because it isn't his wm");
            throw new ResourceAccessException("User with id " + userId + " cannot delete modes, because it isn't his wm");
        }
        final Set<WorkMode> removedSet = getAllModesFromWM(wmId).stream()
                .filter(mode -> (mode.getNameMode().equals("default")))
                .collect(Collectors.toSet());
        model.setWorkModes(removedSet);
        model_repository.save(model);
        List<String> list = new ArrayList<>();
        list.add("Deleting success!");
        return new Status(list, HttpStatus.OK);
    }

    @Override
    @Transactional
    public void deleteWMModel(Long userId, Long modelId) throws ResourceNotFoundException {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found for this id : " + userId));
        final Model internalModel = findById(modelId);
        if (findByUserId(userId).contains(internalModel)) {
            workModeRepository.deleteById(modelId);
        } else {
            LOG.error("User with id " + userId + " cannot delete mode with id " + modelId);
            throw new ResourceAccessException("User with id " + userId + " cannot delete mode with id " + modelId);
        }
    }
}
