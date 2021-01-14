package com.amc.careplanner.service.mapper;


import com.amc.careplanner.domain.*;
import com.amc.careplanner.service.dto.TimesheetDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Timesheet} and its DTO {@link TimesheetDTO}.
 */
@Mapper(componentModel = "spring", uses = {TaskMapper.class, ServiceUserMapper.class, EmployeeMapper.class})
public interface TimesheetMapper extends EntityMapper<TimesheetDTO, Timesheet> {

    @Mapping(source = "task.id", target = "taskId")
    @Mapping(source = "task.taskName", target = "taskTaskName")
    @Mapping(source = "serviceUser.id", target = "serviceUserId")
    @Mapping(source = "serviceUser.serviceUserCode", target = "serviceUserServiceUserCode")
    @Mapping(source = "careProvider.id", target = "careProviderId")
    @Mapping(source = "careProvider.employeeCode", target = "careProviderEmployeeCode")
    TimesheetDTO toDto(Timesheet timesheet);

    @Mapping(source = "taskId", target = "task")
    @Mapping(source = "serviceUserId", target = "serviceUser")
    @Mapping(source = "careProviderId", target = "careProvider")
    Timesheet toEntity(TimesheetDTO timesheetDTO);

    default Timesheet fromId(Long id) {
        if (id == null) {
            return null;
        }
        Timesheet timesheet = new Timesheet();
        timesheet.setId(id);
        return timesheet;
    }
}
