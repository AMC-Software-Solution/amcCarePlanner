package com.amc.careplanner.service.mapper;


import com.amc.careplanner.domain.*;
import com.amc.careplanner.service.dto.CommunicationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Communication} and its DTO {@link CommunicationDTO}.
 */
@Mapper(componentModel = "spring", uses = {ServiceUserMapper.class, EmployeeMapper.class})
public interface CommunicationMapper extends EntityMapper<CommunicationDTO, Communication> {

    @Mapping(source = "serviceUser.id", target = "serviceUserId")
    @Mapping(source = "serviceUser.serviceUserCode", target = "serviceUserServiceUserCode")
    @Mapping(source = "communicatedBy.id", target = "communicatedById")
    @Mapping(source = "communicatedBy.employeeCode", target = "communicatedByEmployeeCode")
    CommunicationDTO toDto(Communication communication);

    @Mapping(source = "serviceUserId", target = "serviceUser")
    @Mapping(source = "communicatedById", target = "communicatedBy")
    Communication toEntity(CommunicationDTO communicationDTO);

    default Communication fromId(Long id) {
        if (id == null) {
            return null;
        }
        Communication communication = new Communication();
        communication.setId(id);
        return communication;
    }
}
