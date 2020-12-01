package com.amc.careplanner.service.mapper;


import com.amc.careplanner.domain.*;
import com.amc.careplanner.service.dto.PowerOfAttorneyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PowerOfAttorney} and its DTO {@link PowerOfAttorneyDTO}.
 */
@Mapper(componentModel = "spring", uses = {ServiceUserMapper.class, EmployeeMapper.class})
public interface PowerOfAttorneyMapper extends EntityMapper<PowerOfAttorneyDTO, PowerOfAttorney> {

    @Mapping(source = "serviceUser.id", target = "serviceUserId")
    @Mapping(source = "serviceUser.serviceUserCode", target = "serviceUserServiceUserCode")
    @Mapping(source = "witnessedBy.id", target = "witnessedById")
    @Mapping(source = "witnessedBy.employeeCode", target = "witnessedByEmployeeCode")
    PowerOfAttorneyDTO toDto(PowerOfAttorney powerOfAttorney);

    @Mapping(source = "serviceUserId", target = "serviceUser")
    @Mapping(source = "witnessedById", target = "witnessedBy")
    PowerOfAttorney toEntity(PowerOfAttorneyDTO powerOfAttorneyDTO);

    default PowerOfAttorney fromId(Long id) {
        if (id == null) {
            return null;
        }
        PowerOfAttorney powerOfAttorney = new PowerOfAttorney();
        powerOfAttorney.setId(id);
        return powerOfAttorney;
    }
}
