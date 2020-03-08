package com.electrolux.facade.impl;

import com.electrolux.entity.Model;
import com.electrolux.exception.ResourceNotFoundException;
import com.electrolux.facade.WashingMachineFacade;
import com.electrolux.services.EntryService;
import com.electrolux.services.WashingMachineService;
import com.electrolux.services.impl.EntryServiceImpl;
import com.electrolux.utils.Status;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;

@Service
@RequiredArgsConstructor
public class WashingMachineFacadeImpl implements WashingMachineFacade {
    private static final Logger LOG = LoggerFactory.getLogger(WashingMachineFacadeImpl.class);

    private final EntryService entryService;
    private final WashingMachineService washingMachineService;

    @Override
    @Transactional
    public Status startWash(@Nonnull Long wmId, Long modeId) {
        final Model model = washingMachineService.findById(wmId);
        model.setWashingNumber(model.getWashingNumber() + 1);
        washingMachineService.updateModel(model.getUser().getId(), wmId, model);
        Status status = entryService.addEntry(wmId, modeId);
        status.getStatus().add("The wash was successfully started!");
        LOG.info(status.getStatus().toString());
        return status;
    }
}
