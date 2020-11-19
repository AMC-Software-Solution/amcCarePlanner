package com.amc.careplanner.service.mapper;


import com.amc.careplanner.domain.*;
import com.amc.careplanner.service.dto.ServiceUserEventDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceUserEvent} and its DTO {@link ServiceUserEventDTO}.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class, ServiceUserMapper.class})
public interface ServiceUserEventMapper extends EntityMapper<ServiceUserEventDTO, ServiceUserEvent> {

    @Mapping(source = "reportedBy.id", target = "reportedById")
    @Mapping(source = "reportedBy.employeeCode", target = "reportedByEmployeeCode")
    @Mapping(source = "assignedTo.id", target = "assignedToId")
    @Mapping(source = "assignedTo.employeeCode", target = "assignedToEmployeeCode")
    @Mapping(source = "serviceUser.id", target = "serviceUserId")
    @Mapping(source = "serviceUser.serviceUserCode", target = "serviceUserServiceUserCode")
    ServiceUserEventDTO toDto(ServiceUserEvent serviceUserEvent);

    @Mapping(source = "reportedById", target = "reportedBy")
    @Mapping(source = "assignedToId", target = "assignedTo")
    @Mapping(source = "serviceUserId", target = "serviceUser")
    ServiceUserEvent toEntity(ServiceUserEventDTO serviceUserEventDTO);

    default ServiceUserEvent fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceUserEvent serviceUserEvent = new ServiceUserEvent();
        serviceUserEvent.setId(id);
        return serviceUserEvent;
    }
}
