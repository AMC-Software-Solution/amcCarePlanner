package com.amc.careplanner.service.mapper;


import com.amc.careplanner.domain.*;
import com.amc.careplanner.service.dto.ConsentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Consent} and its DTO {@link ConsentDTO}.
 */
@Mapper(componentModel = "spring", uses = {ServiceUserMapper.class, EmployeeMapper.class})
public interface ConsentMapper extends EntityMapper<ConsentDTO, Consent> {

    @Mapping(source = "serviceUser.id", target = "serviceUserId")
    @Mapping(source = "serviceUser.serviceUserCode", target = "serviceUserServiceUserCode")
    @Mapping(source = "witnessedBy.id", target = "witnessedById")
    @Mapping(source = "witnessedBy.employeeCode", target = "witnessedByEmployeeCode")
    ConsentDTO toDto(Consent consent);

    @Mapping(source = "serviceUserId", target = "serviceUser")
    @Mapping(source = "witnessedById", target = "witnessedBy")
    Consent toEntity(ConsentDTO consentDTO);

    default Consent fromId(Long id) {
        if (id == null) {
            return null;
        }
        Consent consent = new Consent();
        consent.setId(id);
        return consent;
    }
}
