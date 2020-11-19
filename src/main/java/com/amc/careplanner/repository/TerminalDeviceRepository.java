package com.amc.careplanner.repository;

import com.amc.careplanner.domain.TerminalDevice;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TerminalDevice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TerminalDeviceRepository extends JpaRepository<TerminalDevice, Long>, JpaSpecificationExecutor<TerminalDevice> {
}
