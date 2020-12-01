package com.amc.careplanner.service.mapper;


import com.amc.careplanner.domain.*;
import com.amc.careplanner.service.dto.ServceUserDocumentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServceUserDocument} and its DTO {@link ServceUserDocumentDTO}.
 */
@Mapper(componentModel = "spring", uses = {ServiceUserMapper.class, EmployeeMapper.class})
public interface ServceUserDocumentMapper extends EntityMapper<ServceUserDocumentDTO, ServceUserDocument> {

    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "owner.serviceUserCode", target = "ownerServiceUserCode")
    @Mapping(source = "approvedBy.id", target = "approvedById")
    @Mapping(source = "approvedBy.employeeCode", target = "approvedByEmployeeCode")
    ServceUserDocumentDTO toDto(ServceUserDocument servceUserDocument);

    @Mapping(source = "ownerId", target = "owner")
    @Mapping(source = "approvedById", target = "approvedBy")
    ServceUserDocument toEntity(ServceUserDocumentDTO servceUserDocumentDTO);

    default ServceUserDocument fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServceUserDocument servceUserDocument = new ServceUserDocument();
        servceUserDocument.setId(id);
        return servceUserDocument;
    }
}
