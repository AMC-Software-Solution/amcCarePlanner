package com.amc.careplanner.service.mapper;


import com.amc.careplanner.domain.*;
import com.amc.careplanner.service.dto.DisabilityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Disability} and its DTO {@link DisabilityDTO}.
 */
@Mapper(componentModel = "spring", uses = {DisabilityTypeMapper.class, EmployeeMapper.class})
public interface DisabilityMapper extends EntityMapper<DisabilityDTO, Disability> {

    @Mapping(source = "disabilityType.id", target = "disabilityTypeId")
    @Mapping(source = "disabilityType.disability", target = "disabilityTypeDisability")
    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "employee.employeeCode", target = "employeeEmployeeCode")
    DisabilityDTO toDto(Disability disability);

    @Mapping(source = "disabilityTypeId", target = "disabilityType")
    @Mapping(source = "employeeId", target = "employee")
    Disability toEntity(DisabilityDTO disabilityDTO);

    default Disability fromId(Long id) {
        if (id == null) {
            return null;
        }
        Disability disability = new Disability();
        disability.setId(id);
        return disability;
    }
}
