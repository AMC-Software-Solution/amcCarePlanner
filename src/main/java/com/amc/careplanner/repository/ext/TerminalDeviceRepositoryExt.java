package com.amc.careplanner.repository.ext;

import com.amc.careplanner.domain.TerminalDevice;
import com.amc.careplanner.repository.TerminalDeviceRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TerminalDevice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TerminalDeviceRepositoryExt extends TerminalDeviceRepository{
}
