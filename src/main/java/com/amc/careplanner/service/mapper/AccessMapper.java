package com.amc.careplanner.service.mapper;


import com.amc.careplanner.domain.*;
import com.amc.careplanner.service.dto.AccessDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Access} and its DTO {@link AccessDTO}.
 */
@Mapper(componentModel = "spring", uses = {ServiceUserMapper.class})
public interface AccessMapper extends EntityMapper<AccessDTO, Access> {

    @Mapping(source = "serviceUser.id", target = "serviceUserId")
    @Mapping(source = "serviceUser.serviceUserCode", target = "serviceUserServiceUserCode")
    AccessDTO toDto(Access access);

    @Mapping(source = "serviceUserId", target = "serviceUser")
    Access toEntity(AccessDTO accessDTO);

    default Access fromId(Long id) {
        if (id == null) {
            return null;
        }
        Access access = new Access();
        access.setId(id);
        return access;
    }
}
