package com.amc.careplanner.service.mapper;


import com.amc.careplanner.domain.*;
import com.amc.careplanner.service.dto.CarerServiceUserRelationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CarerServiceUserRelation} and its DTO {@link CarerServiceUserRelationDTO}.
 */
@Mapper(componentModel = "spring", uses = {RelationshipTypeMapper.class, EmployeeMapper.class, ServiceUserMapper.class})
public interface CarerServiceUserRelationMapper extends EntityMapper<CarerServiceUserRelationDTO, CarerServiceUserRelation> {

    @Mapping(source = "relationType.id", target = "relationTypeId")
    @Mapping(source = "relationType.relationType", target = "relationTypeRelationType")
    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "employee.employeeCode", target = "employeeEmployeeCode")
    @Mapping(source = "serviceUser.id", target = "serviceUserId")
    @Mapping(source = "serviceUser.serviceUserCode", target = "serviceUserServiceUserCode")
    CarerServiceUserRelationDTO toDto(CarerServiceUserRelation carerServiceUserRelation);

    @Mapping(source = "relationTypeId", target = "relationType")
    @Mapping(source = "employeeId", target = "employee")
    @Mapping(source = "serviceUserId", target = "serviceUser")
    CarerServiceUserRelation toEntity(CarerServiceUserRelationDTO carerServiceUserRelationDTO);

    default CarerServiceUserRelation fromId(Long id) {
        if (id == null) {
            return null;
        }
        CarerServiceUserRelation carerServiceUserRelation = new CarerServiceUserRelation();
        carerServiceUserRelation.setId(id);
        return carerServiceUserRelation;
    }
}
