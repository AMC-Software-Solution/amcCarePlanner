package com.amc.careplanner.service.mapper;


import com.amc.careplanner.domain.*;
import com.amc.careplanner.service.dto.EmployeeAvailabilityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmployeeAvailability} and its DTO {@link EmployeeAvailabilityDTO}.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface EmployeeAvailabilityMapper extends EntityMapper<EmployeeAvailabilityDTO, EmployeeAvailability> {

    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "employee.employeeCode", target = "employeeEmployeeCode")
    EmployeeAvailabilityDTO toDto(EmployeeAvailability employeeAvailability);

    @Mapping(source = "employeeId", target = "employee")
    EmployeeAvailability toEntity(EmployeeAvailabilityDTO employeeAvailabilityDTO);

    default EmployeeAvailability fromId(Long id) {
        if (id == null) {
            return null;
        }
        EmployeeAvailability employeeAvailability = new EmployeeAvailability();
        employeeAvailability.setId(id);
        return employeeAvailability;
    }
}
