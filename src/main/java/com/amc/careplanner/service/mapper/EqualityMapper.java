package com.amc.careplanner.service.mapper;


import com.amc.careplanner.domain.*;
import com.amc.careplanner.service.dto.EqualityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Equality} and its DTO {@link EqualityDTO}.
 */
@Mapper(componentModel = "spring", uses = {CountryMapper.class, ServiceUserMapper.class})
public interface EqualityMapper extends EntityMapper<EqualityDTO, Equality> {

    @Mapping(source = "nationality.id", target = "nationalityId")
    @Mapping(source = "nationality.countryName", target = "nationalityCountryName")
    @Mapping(source = "serviceUser.id", target = "serviceUserId")
    @Mapping(source = "serviceUser.serviceUserCode", target = "serviceUserServiceUserCode")
    EqualityDTO toDto(Equality equality);

    @Mapping(source = "nationalityId", target = "nationality")
    @Mapping(source = "serviceUserId", target = "serviceUser")
    Equality toEntity(EqualityDTO equalityDTO);

    default Equality fromId(Long id) {
        if (id == null) {
            return null;
        }
        Equality equality = new Equality();
        equality.setId(id);
        return equality;
    }
}
