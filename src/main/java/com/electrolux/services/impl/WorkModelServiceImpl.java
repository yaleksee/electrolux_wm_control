package com.electrolux.services.impl;

import com.electrolux.entity.User;
import com.electrolux.entity.WorkMode;
import com.electrolux.exception.ResourceAccessException;
import com.electrolux.exception.ResourceNotFoundException;
import com.electrolux.repository.UserRepository;
import com.electrolux.repository.WorkModeRepository;
import com.electrolux.services.WorkModelService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class WorkModelServiceImpl implements WorkModelService {
    private static final Logger LOG = LoggerFactory.getLogger(WorkModelServiceImpl.class);

    private final WorkModeRepository workModeRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public WorkMode createWorkMode(Long userId, WorkMode newMode) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found for this id : " + userId));
        final WorkMode workMode = workModeRepository.findByNameMode(newMode.getNameMode());
        if (workMode != null) {
            LOG.error("Mode with name " + newMode.getNameMode() + " already exist");
            throw new ResourceAccessException("Mode with name " + newMode.getNameMode() + " already exist");
        }
        newMode.setUser(user);
        return workModeRepository.save(newMode);
    }

    @Override
    @Transactional
    public WorkMode updateWorkMode(Long userId, Long modeId, WorkMode externalMode) throws ResourceNotFoundException {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found for this id : " + userId));
        final String modeName = externalMode.getNameMode();
        final WorkMode mode = workModeRepository.findByNameMode(modeName);
        if (mode != null) {
            LOG.error("mode with name + " + modeName + " already exists");
            throw new ResourceAccessException("mode with name + " + modeName + " already exists");
        }
        final WorkMode internalWorkMode = findById(modeId);
        if (!internalWorkMode.getNameMode().equals(externalMode.getNameMode())) {
            LOG.error("Change mode name impossible");
            throw new ResourceAccessException("Change mode name impossible");
        }
        if (findByUserId(userId).contains(internalWorkMode)) {
            WorkMode internalMode = findById(modeId);
            internalMode.setNameMode(externalMode.getNameMode());
            internalMode.setSaveWater(externalMode.getSaveWater());
            internalMode.setSpidSpeed(externalMode.getSpidSpeed());
            internalMode.setWashingTimer(externalMode.getWashingTimer());
            internalMode.setWashingTemperature(externalMode.getWashingTemperature());
            return workModeRepository.save(internalMode);
        }
        LOG.error("User with id : " + userId + " don't author of this mode");
        throw new ResourceNotFoundException("User with id : " + userId + " don't author of this mode");
    }

    @Override
    public WorkMode findById(long modeId) throws ResourceNotFoundException {
        return workModeRepository.findById(modeId)
                .orElseThrow(() -> new ResourceNotFoundException("workMode not found for this id : " + modeId));
    }

    @Override
    public WorkMode findByNameMode(String nameMode) throws ResourceNotFoundException {
        final WorkMode workMode = workModeRepository.findByNameMode(nameMode);
        if (workMode == null) {
            LOG.error("workMode not found for this name : " + nameMode);
            throw new ResourceNotFoundException("workMode not found for this name : " + nameMode);
        }
        return workMode;
    }

    @Override
    public List<WorkMode> getAllWorkModes() {
        return workModeRepository.findAll();
    }

    @Override
    public Set<WorkMode> findByUserId(Long userId) {
        final Set<WorkMode> workModes = workModeRepository.findByUserId(userId);
        if (workModes.size() == 0) {
            LOG.error("workModes not found for user with id: " + userId);
            throw new ResourceNotFoundException("workModes not found for user with id: " + userId);
        }
        return workModes;
    }

    @Override
    @Transactional
    public void deleteWorkMode(Long userId, Long modeId) throws ResourceNotFoundException {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found for this id : " + userId));
        final WorkMode internalWorkMode = findById(modeId);
        if (findByUserId(userId).contains(internalWorkMode)) {
            workModeRepository.deleteById(modeId);
        } else {
            LOG.error("User with id " + userId + " cannot delete mode with id " + modeId);
            throw new ResourceAccessException("User with id " + userId + " cannot delete mode with id " + modeId);
        }
    }

}
