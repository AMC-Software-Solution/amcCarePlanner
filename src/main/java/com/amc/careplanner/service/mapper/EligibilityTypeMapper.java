package com.amc.careplanner.service.mapper;


import com.amc.careplanner.domain.*;
import com.amc.careplanner.service.dto.EligibilityTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EligibilityType} and its DTO {@link EligibilityTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EligibilityTypeMapper extends EntityMapper<EligibilityTypeDTO, EligibilityType> {



    default EligibilityType fromId(Long id) {
        if (id == null) {
            return null;
        }
        EligibilityType eligibilityType = new EligibilityType();
        eligibilityType.setId(id);
        return eligibilityType;
    }
}
