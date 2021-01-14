package com.amc.careplanner.service.mapper;


import com.amc.careplanner.domain.*;
import com.amc.careplanner.service.dto.EmployeeDesignationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmployeeDesignation} and its DTO {@link EmployeeDesignationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EmployeeDesignationMapper extends EntityMapper<EmployeeDesignationDTO, EmployeeDesignation> {



    default EmployeeDesignation fromId(Long id) {
        if (id == null) {
            return null;
        }
        EmployeeDesignation employeeDesignation = new EmployeeDesignation();
        employeeDesignation.setId(id);
        return employeeDesignation;
    }
}
