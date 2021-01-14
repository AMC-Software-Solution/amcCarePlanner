package com.amc.careplanner.service.mapper;


import com.amc.careplanner.domain.*;
import com.amc.careplanner.service.dto.RelationshipTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RelationshipType} and its DTO {@link RelationshipTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RelationshipTypeMapper extends EntityMapper<RelationshipTypeDTO, RelationshipType> {



    default RelationshipType fromId(Long id) {
        if (id == null) {
            return null;
        }
        RelationshipType relationshipType = new RelationshipType();
        relationshipType.setId(id);
        return relationshipType;
    }
}
