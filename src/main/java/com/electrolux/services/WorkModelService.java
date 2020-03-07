package com.electrolux.services;

import com.electrolux.entity.WorkMode;
import com.electrolux.exception.ResourceNotFoundException;
import com.electrolux.repository.UserRepository;
import com.electrolux.repository.WorkModeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkModelService {

    private final WorkModeRepository workModeRepository;
    private final UserRepository userRepository;

    // создать режим и привязать его к user
    public WorkMode createWorkMode(Long userId, WorkMode newMode) {
        return userRepository.findById(userId).map(user -> {
            newMode.setUser(user);
            return workModeRepository.save(newMode);
        }).orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));
    }

    // редактировать режим
    public WorkMode updateWorkMode(Long modeId, WorkMode externalMode) throws ResourceNotFoundException {
        final WorkMode internalMode = findById(modeId);
        internalMode.setNameMode(externalMode.getNameMode());
        internalMode.setSaveWater(externalMode.getSaveWater());
        internalMode.setSpidSpeed(externalMode.getSpidSpeed());
        internalMode.setWashingTemperature(externalMode.getWashingTemperature());
        return workModeRepository.save(internalMode);
    }

    // выбрать режим по id
    public WorkMode findById(long modeId) throws ResourceNotFoundException {
        WorkMode workMode = workModeRepository.findById(modeId)
                .orElseThrow(() -> new ResourceNotFoundException("workMode not found for this id : " + modeId));
        return workMode;
    }

    // выбрать режим по nameMode
    public WorkMode findById(String nameMode) throws ResourceNotFoundException {
        WorkMode workMode = workModeRepository.findByNameMode(nameMode);
        if (workMode == null) new ResourceNotFoundException("workMode not found for this name : " + nameMode);
        return workMode;
    }

    // выбрать все существующие режимы
    public List<WorkMode> getAllWorkModes() {
        return workModeRepository.findAll();
    }

    // выбрать все существующие режимы у данного пользователя
    public Page<WorkMode> findByUserId(Long userId, Pageable pageable) {
        return workModeRepository.findByUserId(userId, pageable);
    }

    // выбрать стиральные машины в которые установлен режим по его nameMode


    // удалить режим
    public void deleteWorkMode(Long userId, Long modeId) throws ResourceNotFoundException {
        if (workModeRepository.findByIdAndUserId(modeId, userId) != null)
            workModeRepository.deleteById(modeId);
    }

}
