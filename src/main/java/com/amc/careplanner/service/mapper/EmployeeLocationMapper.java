package com.amc.careplanner.service.mapper;


import com.amc.careplanner.domain.*;
import com.amc.careplanner.service.dto.EmployeeLocationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmployeeLocation} and its DTO {@link EmployeeLocationDTO}.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface EmployeeLocationMapper extends EntityMapper<EmployeeLocationDTO, EmployeeLocation> {

    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "employee.employeeCode", target = "employeeEmployeeCode")
    EmployeeLocationDTO toDto(EmployeeLocation employeeLocation);

    @Mapping(source = "employeeId", target = "employee")
    EmployeeLocation toEntity(EmployeeLocationDTO employeeLocationDTO);

    default EmployeeLocation fromId(Long id) {
        if (id == null) {
            return null;
        }
        EmployeeLocation employeeLocation = new EmployeeLocation();
        employeeLocation.setId(id);
        return employeeLocation;
    }
}
