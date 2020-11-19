package com.amc.careplanner.service.mapper;


import com.amc.careplanner.domain.*;
import com.amc.careplanner.service.dto.CarerClientRelationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CarerClientRelation} and its DTO {@link CarerClientRelationDTO}.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class, ServiceUserMapper.class})
public interface CarerClientRelationMapper extends EntityMapper<CarerClientRelationDTO, CarerClientRelation> {

    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "employee.employeeCode", target = "employeeEmployeeCode")
    @Mapping(source = "serviceUser.id", target = "serviceUserId")
    @Mapping(source = "serviceUser.serviceUserCode", target = "serviceUserServiceUserCode")
    CarerClientRelationDTO toDto(CarerClientRelation carerClientRelation);

    @Mapping(source = "employeeId", target = "employee")
    @Mapping(source = "serviceUserId", target = "serviceUser")
    CarerClientRelation toEntity(CarerClientRelationDTO carerClientRelationDTO);

    default CarerClientRelation fromId(Long id) {
        if (id == null) {
            return null;
        }
        CarerClientRelation carerClientRelation = new CarerClientRelation();
        carerClientRelation.setId(id);
        return carerClientRelation;
    }
}
