package com.electrolux.services;

import com.electrolux.entity.User;
import com.electrolux.entity.WM_Model;
import com.electrolux.entity.WorkMode;
import com.electrolux.exception.ResourceAccessException;
import com.electrolux.exception.ResourceNotFoundException;
import com.electrolux.repository.UserRepository;
import com.electrolux.repository.WorkModeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class WorkModelService {

    private final WorkModeRepository workModeRepository;
    private final UserRepository userRepository;

    // создать режим и привязать его к user
    public WorkMode createWorkMode(Long userId, WorkMode newMode) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found for this id : " + userId));
        WorkMode workMode = workModeRepository.findByNameMode(newMode.getNameMode());
        if (workMode != null) {
            throw new ResourceAccessException("Mode with name " + newMode.getNameMode() + " already exist");
        }
        newMode.setUser(user);
        return workModeRepository.save(newMode);
    }

    // редактировать режим может только user который этот режим создал
    public WorkMode updateWorkMode(Long userId, Long modeId, WorkMode externalMode) throws ResourceNotFoundException {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found for this id : " + userId));
        String modeName = externalMode.getNameMode();
        WorkMode mode = workModeRepository.findByNameMode(modeName);
        if (mode != null)
            throw new ResourceAccessException("mode with name + " + modeName + " already exists");
        WorkMode internalWorkMode = findById(modeId);
        if (findByUserId(userId).contains(internalWorkMode)) {
            final WorkMode internalMode = findById(modeId);
            internalMode.setNameMode(externalMode.getNameMode());
            internalMode.setSaveWater(externalMode.getSaveWater());
            internalMode.setSpidSpeed(externalMode.getSpidSpeed());
            internalMode.setWashingTemperature(externalMode.getWashingTemperature());
            return workModeRepository.save(internalMode);
        }
        throw new ResourceNotFoundException("User with id : " + userId + " don't author of this mode");
    }

    // выбрать режим по id
    public WorkMode findById(long modeId) throws ResourceNotFoundException {
        WorkMode workMode = workModeRepository.findById(modeId)
                .orElseThrow(() -> new ResourceNotFoundException("workMode not found for this id : " + modeId));
        return workMode;
    }

    // выбрать режим по nameMode
    public WorkMode findByNameMode(String nameMode) throws ResourceNotFoundException {
        WorkMode workMode = workModeRepository.findByNameMode(nameMode);
        if (workMode == null) throw new ResourceNotFoundException("workMode not found for this name : " + nameMode);
        return workMode;
    }

    // выбрать все существующие режимы
    public List<WorkMode> getAllWorkModes() {
        return workModeRepository.findAll();
    }

    // выбрать все существующие режимы у данного пользователя
    public Set<WorkMode> findByUserId(Long userId) {
        Set<WorkMode> workModes = workModeRepository.findByUserId(userId);
        if (workModes == null) throw new ResourceNotFoundException("workModes not found for user with id: " + userId);
        return workModes;
    }

    // удалить режим может только юзер который его создал
    public void deleteWorkMode(Long userId, Long modeId) throws ResourceNotFoundException {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found for this id : " + userId));
        WorkMode internalWorkMode = findById(modeId);
        if (findByUserId(userId).contains(internalWorkMode)) {
            workModeRepository.deleteById(modeId);
        } else {
            throw new ResourceAccessException("User with id " + userId + " cannot delete mode with id " + modeId);
        }
    }

}
