package com.amc.careplanner.service.mapper;


import com.amc.careplanner.domain.*;
import com.amc.careplanner.service.dto.PayrollDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Payroll} and its DTO {@link PayrollDTO}.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class, TimesheetMapper.class})
public interface PayrollMapper extends EntityMapper<PayrollDTO, Payroll> {

    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "employee.employeeCode", target = "employeeEmployeeCode")
    @Mapping(source = "timesheet.id", target = "timesheetId")
    @Mapping(source = "timesheet.description", target = "timesheetDescription")
    PayrollDTO toDto(Payroll payroll);

    @Mapping(source = "employeeId", target = "employee")
    @Mapping(source = "timesheetId", target = "timesheet")
    Payroll toEntity(PayrollDTO payrollDTO);

    default Payroll fromId(Long id) {
        if (id == null) {
            return null;
        }
        Payroll payroll = new Payroll();
        payroll.setId(id);
        return payroll;
    }
}
