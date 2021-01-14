package com.amc.careplanner.service.mapper;


import com.amc.careplanner.domain.*;
import com.amc.careplanner.service.dto.ExtraDataDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ExtraData} and its DTO {@link ExtraDataDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ExtraDataMapper extends EntityMapper<ExtraDataDTO, ExtraData> {



    default ExtraData fromId(Long id) {
        if (id == null) {
            return null;
        }
        ExtraData extraData = new ExtraData();
        extraData.setId(id);
        return extraData;
    }
}
