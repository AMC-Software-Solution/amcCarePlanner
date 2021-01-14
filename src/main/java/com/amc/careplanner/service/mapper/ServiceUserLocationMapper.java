package com.amc.careplanner.service.mapper;


import com.amc.careplanner.domain.*;
import com.amc.careplanner.service.dto.ServiceUserLocationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceUserLocation} and its DTO {@link ServiceUserLocationDTO}.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface ServiceUserLocationMapper extends EntityMapper<ServiceUserLocationDTO, ServiceUserLocation> {

    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "employee.employeeCode", target = "employeeEmployeeCode")
    ServiceUserLocationDTO toDto(ServiceUserLocation serviceUserLocation);

    @Mapping(source = "employeeId", target = "employee")
    ServiceUserLocation toEntity(ServiceUserLocationDTO serviceUserLocationDTO);

    default ServiceUserLocation fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceUserLocation serviceUserLocation = new ServiceUserLocation();
        serviceUserLocation.setId(id);
        return serviceUserLocation;
    }
}
