package com.amc.careplanner.repository;

import com.amc.careplanner.domain.SystemSetting;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SystemSetting entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SystemSettingRepository extends JpaRepository<SystemSetting, Long>, JpaSpecificationExecutor<SystemSetting> {
}
