package com.amc.careplanner.service.mapper;


import com.amc.careplanner.domain.*;
import com.amc.careplanner.service.dto.TaskDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Task} and its DTO {@link TaskDTO}.
 */
@Mapper(componentModel = "spring", uses = {ServiceUserMapper.class, EmployeeMapper.class, ServiceOrderMapper.class})
public interface TaskMapper extends EntityMapper<TaskDTO, Task> {

    @Mapping(source = "serviceUser.id", target = "serviceUserId")
    @Mapping(source = "serviceUser.serviceUserCode", target = "serviceUserServiceUserCode")
    @Mapping(source = "assignedTo.id", target = "assignedToId")
    @Mapping(source = "assignedTo.employeeCode", target = "assignedToEmployeeCode")
    @Mapping(source = "serviceOrder.id", target = "serviceOrderId")
    @Mapping(source = "serviceOrder.title", target = "serviceOrderTitle")
    @Mapping(source = "createdBy.id", target = "createdById")
    @Mapping(source = "createdBy.employeeCode", target = "createdByEmployeeCode")
    @Mapping(source = "allocatedBy.id", target = "allocatedById")
    @Mapping(source = "allocatedBy.employeeCode", target = "allocatedByEmployeeCode")
    TaskDTO toDto(Task task);

    @Mapping(source = "serviceUserId", target = "serviceUser")
    @Mapping(source = "assignedToId", target = "assignedTo")
    @Mapping(source = "serviceOrderId", target = "serviceOrder")
    @Mapping(source = "createdById", target = "createdBy")
    @Mapping(source = "allocatedById", target = "allocatedBy")
    Task toEntity(TaskDTO taskDTO);

    default Task fromId(Long id) {
        if (id == null) {
            return null;
        }
        Task task = new Task();
        task.setId(id);
        return task;
    }
}
