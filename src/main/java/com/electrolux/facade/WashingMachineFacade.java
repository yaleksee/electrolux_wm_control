package com.electrolux.facade;

import com.electrolux.utils.Status;

import javax.annotation.Nonnull;

public interface WashingMachineFacade {

    /**
     * The selected washing mode is started on the selected stirling machine.
     * The washing number for this washing machine is increasing.
     * A new wash entry appears.
     * The status returns the description of the owner of the selected washing machine,
     * a description of the selected mode and characteristics of the strip machine.
     *
     * @param wmid   - id washing machine
     * @param modeId - id mode
     * @return {@link Status}
     */
    Status startWash(@Nonnull Long wmid, Long modeId);
}
