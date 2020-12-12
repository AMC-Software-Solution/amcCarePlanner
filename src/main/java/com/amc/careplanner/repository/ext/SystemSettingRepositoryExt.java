package com.amc.careplanner.repository.ext;

import com.amc.careplanner.domain.SystemSetting;
import com.amc.careplanner.repository.SystemSettingRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SystemSetting entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SystemSettingRepositoryExt extends SystemSettingRepository{
}
