package com.electrolux.services;

import com.electrolux.entity.Model;
import com.electrolux.entity.User;
import com.electrolux.entity.WorkMode;
import com.electrolux.exception.ResourceAccessException;
import com.electrolux.exception.ResourceNotFoundException;
import com.electrolux.repository.UserRepository;
import com.electrolux.repository.WorkModeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Set;

public interface WorkModeService {

    /**
     * Create a mode and bind it to user
     *
     * @param userId  - id user
     * @param newMode - {@link WorkMode}
     * @return if user is found - {@link WorkMode}, else - ResourceAccessException.
     */
    WorkMode createWorkMode(@Nonnull Long userId, WorkMode newMode);

    /**
     * only the user who created this mode can edit the mode
     *
     * @param userId       - id user
     * @param modeId       - id workMode
     * @param externalMode - {@link WorkMode}
     * @return if user is found - {@link WorkMode}, else - ResourceAccessException.
     */
    WorkMode updateWorkMode(@Nonnull Long userId, Long modeId, WorkMode externalMode);


    WorkMode findById(@Nonnull Long modeId);

    WorkMode findByNameMode(@Nonnull String nameMode);

    List<WorkMode> getAllWorkModes();

    Set<WorkMode> findByUserId(@Nonnull Long userId);

    /**
     * only the user who created it can delete the mode
     *
     * @param userId - id user
     * @param modeId - id mode
     */
    void deleteWorkMode(@Nonnull Long userId, Long modeId);

}
