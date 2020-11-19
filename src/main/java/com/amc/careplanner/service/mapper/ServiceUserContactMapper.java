package com.amc.careplanner.service.mapper;


import com.amc.careplanner.domain.*;
import com.amc.careplanner.service.dto.ServiceUserContactDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceUserContact} and its DTO {@link ServiceUserContactDTO}.
 */
@Mapper(componentModel = "spring", uses = {ServiceUserMapper.class})
public interface ServiceUserContactMapper extends EntityMapper<ServiceUserContactDTO, ServiceUserContact> {

    @Mapping(source = "serviceUser.id", target = "serviceUserId")
    @Mapping(source = "serviceUser.serviceUserCode", target = "serviceUserServiceUserCode")
    ServiceUserContactDTO toDto(ServiceUserContact serviceUserContact);

    @Mapping(source = "serviceUserId", target = "serviceUser")
    ServiceUserContact toEntity(ServiceUserContactDTO serviceUserContactDTO);

    default ServiceUserContact fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceUserContact serviceUserContact = new ServiceUserContact();
        serviceUserContact.setId(id);
        return serviceUserContact;
    }
}
