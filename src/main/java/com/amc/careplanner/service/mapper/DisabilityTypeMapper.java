package com.amc.careplanner.service.mapper;


import com.amc.careplanner.domain.*;
import com.amc.careplanner.service.dto.DisabilityTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DisabilityType} and its DTO {@link DisabilityTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DisabilityTypeMapper extends EntityMapper<DisabilityTypeDTO, DisabilityType> {



    default DisabilityType fromId(Long id) {
        if (id == null) {
            return null;
        }
        DisabilityType disabilityType = new DisabilityType();
        disabilityType.setId(id);
        return disabilityType;
    }
}
