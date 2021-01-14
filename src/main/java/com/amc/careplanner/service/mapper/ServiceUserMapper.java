package com.amc.careplanner.service.mapper;


import com.amc.careplanner.domain.*;
import com.amc.careplanner.service.dto.ServiceUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceUser} and its DTO {@link ServiceUserDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, BranchMapper.class, EmployeeMapper.class})
public interface ServiceUserMapper extends EntityMapper<ServiceUserDTO, ServiceUser> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.email", target = "userEmail")
    @Mapping(source = "branch.id", target = "branchId")
    @Mapping(source = "branch.name", target = "branchName")
    @Mapping(source = "registeredBy.id", target = "registeredById")
    @Mapping(source = "registeredBy.employeeCode", target = "registeredByEmployeeCode")
    @Mapping(source = "activatedBy.id", target = "activatedById")
    @Mapping(source = "activatedBy.employeeCode", target = "activatedByEmployeeCode")
    ServiceUserDTO toDto(ServiceUser serviceUser);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "branchId", target = "branch")
    @Mapping(source = "registeredById", target = "registeredBy")
    @Mapping(source = "activatedById", target = "activatedBy")
    ServiceUser toEntity(ServiceUserDTO serviceUserDTO);

    default ServiceUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceUser serviceUser = new ServiceUser();
        serviceUser.setId(id);
        return serviceUser;
    }
}
