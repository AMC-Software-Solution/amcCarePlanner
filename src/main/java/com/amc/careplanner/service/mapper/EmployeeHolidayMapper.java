package com.amc.careplanner.service.mapper;


import com.amc.careplanner.domain.*;
import com.amc.careplanner.service.dto.EmployeeHolidayDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmployeeHoliday} and its DTO {@link EmployeeHolidayDTO}.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface EmployeeHolidayMapper extends EntityMapper<EmployeeHolidayDTO, EmployeeHoliday> {

    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "employee.employeeCode", target = "employeeEmployeeCode")
    @Mapping(source = "approvedBy.id", target = "approvedById")
    @Mapping(source = "approvedBy.employeeCode", target = "approvedByEmployeeCode")
    EmployeeHolidayDTO toDto(EmployeeHoliday employeeHoliday);

    @Mapping(source = "employeeId", target = "employee")
    @Mapping(source = "approvedById", target = "approvedBy")
    EmployeeHoliday toEntity(EmployeeHolidayDTO employeeHolidayDTO);

    default EmployeeHoliday fromId(Long id) {
        if (id == null) {
            return null;
        }
        EmployeeHoliday employeeHoliday = new EmployeeHoliday();
        employeeHoliday.setId(id);
        return employeeHoliday;
    }
}
