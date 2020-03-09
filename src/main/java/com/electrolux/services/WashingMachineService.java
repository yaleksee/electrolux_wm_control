package com.electrolux.services;

import com.electrolux.entity.User;
import com.electrolux.entity.Model;
import com.electrolux.entity.WorkMode;
import com.electrolux.exception.ResourceAccessException;
import com.electrolux.exception.ResourceNotFoundException;
import com.electrolux.repository.UserRepository;
import com.electrolux.repository.ModelRepository;
import com.electrolux.repository.WorkModeRepository;
import com.electrolux.utils.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface WashingMachineService {

    /**
     * Create WM and bind it to user
     * Load the 'default' wash mode into WM
     *
     * @param userId   - id
     * @param newModel - {@link Model}
     * @return if user by userId is found - {@link Model}, else - ResourceNotFoundException.
     */
    Model createModel(Long userId, Model newModel);

    /**
     * Only the user who owns this machine can edit WM.
     * Change the warranty period is not possible.
     *
     * @param userId        - id user
     * @param modelId       - id model
     * @param externalModel {@link Model}
     * @return if user and model are found - {@link Model}, else - ResourceNotFoundException.
     */
    Model updateModel(@Nonnull Long userId, Long modelId, Model externalModel);

    Model findById(@Nonnull Long modelId);

    Model findByNameModel(@Nonnull String modelName);

    List<Model> getAllWM_Models();

    Set<Model> findByUserId(@Nonnull Long userId);

    /**
     * Get all exiting modes from WM by id
     *
     * @param wmId - washing machine
     * @return if washing machine is found - {@link WorkMode}, else - ResourceNotFoundException.
     */
    Set<WorkMode> getAllModesFromWM(@Nonnull Long wmId);


    /**
     * User loads the current mode into the current model by mode id
     * User does not have access to modes that do not beLong to him
     *
     * @param userId    - id user
     * @param wmId      - id washing machine
     * @param arrModeId - collection id modes
     * @return {@link Status}
     */
    Status putSomeModeIntoWM(@Nonnull Long userId, Long wmId, List<Long> arrModeId);


    /**
     * User loads all his wash modes into the current model
     *
     * @param userId - id user
     * @param wmId   - id washing machine
     * @return {@link Status}
     */
    Status putAllModeIntoWM(@Nonnull Long userId, Long wmId);


    /**
     * User deletes washing modes by their id except for the default mode "default"
     *
     * @param userId    - id user
     * @param wmId      - id washine machine
     * @param arrModeId - collection id modes
     * @return {@link Status}
     */
    Status deleteSomeModeIntoWM(@Nonnull Long userId, Long wmId, List<Long> arrModeId);


    /**
     * User deletes all washing modes except the default mode "default"
     *
     * @param userId - id user
     * @param wmId   - id washing machine
     * @return {@link Status}
     */
    Status deleteAllModeIntoWM(@Nonnull Long userId, Long wmId);


    /**
     * Only the user who created WM can delete it
     *
     * @param userId  - id user
     * @param modelId - id washing machine
     */
    void deleteWMModel(@Nonnull Long userId, Long modelId);
}
