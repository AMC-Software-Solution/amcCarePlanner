package com.amc.careplanner.service.mapper;


import com.amc.careplanner.domain.*;
import com.amc.careplanner.service.dto.SystemSettingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SystemSetting} and its DTO {@link SystemSettingDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SystemSettingMapper extends EntityMapper<SystemSettingDTO, SystemSetting> {



    default SystemSetting fromId(Long id) {
        if (id == null) {
            return null;
        }
        SystemSetting systemSetting = new SystemSetting();
        systemSetting.setId(id);
        return systemSetting;
    }
}
