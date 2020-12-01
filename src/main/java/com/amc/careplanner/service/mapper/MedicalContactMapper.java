package com.amc.careplanner.service.mapper;


import com.amc.careplanner.domain.*;
import com.amc.careplanner.service.dto.MedicalContactDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MedicalContact} and its DTO {@link MedicalContactDTO}.
 */
@Mapper(componentModel = "spring", uses = {ServiceUserMapper.class})
public interface MedicalContactMapper extends EntityMapper<MedicalContactDTO, MedicalContact> {

    @Mapping(source = "serviceUser.id", target = "serviceUserId")
    @Mapping(source = "serviceUser.serviceUserCode", target = "serviceUserServiceUserCode")
    MedicalContactDTO toDto(MedicalContact medicalContact);

    @Mapping(source = "serviceUserId", target = "serviceUser")
    MedicalContact toEntity(MedicalContactDTO medicalContactDTO);

    default MedicalContact fromId(Long id) {
        if (id == null) {
            return null;
        }
        MedicalContact medicalContact = new MedicalContact();
        medicalContact.setId(id);
        return medicalContact;
    }
}
