package com.amc.careplanner.service.mapper;


import com.amc.careplanner.domain.*;
import com.amc.careplanner.service.dto.EmergencyContactDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmergencyContact} and its DTO {@link EmergencyContactDTO}.
 */
@Mapper(componentModel = "spring", uses = {ServiceUserMapper.class})
public interface EmergencyContactMapper extends EntityMapper<EmergencyContactDTO, EmergencyContact> {

    @Mapping(source = "serviceUser.id", target = "serviceUserId")
    @Mapping(source = "serviceUser.serviceUserCode", target = "serviceUserServiceUserCode")
    EmergencyContactDTO toDto(EmergencyContact emergencyContact);

    @Mapping(source = "serviceUserId", target = "serviceUser")
    EmergencyContact toEntity(EmergencyContactDTO emergencyContactDTO);

    default EmergencyContact fromId(Long id) {
        if (id == null) {
            return null;
        }
        EmergencyContact emergencyContact = new EmergencyContact();
        emergencyContact.setId(id);
        return emergencyContact;
    }
}
