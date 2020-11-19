package com.amc.careplanner.service.mapper;


import com.amc.careplanner.domain.*;
import com.amc.careplanner.service.dto.TenantDocumentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TenantDocument} and its DTO {@link TenantDocumentDTO}.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface TenantDocumentMapper extends EntityMapper<TenantDocumentDTO, TenantDocument> {

    @Mapping(source = "uploadedBy.id", target = "uploadedById")
    @Mapping(source = "uploadedBy.employeeCode", target = "uploadedByEmployeeCode")
    @Mapping(source = "approvedBy.id", target = "approvedById")
    @Mapping(source = "approvedBy.employeeCode", target = "approvedByEmployeeCode")
    TenantDocumentDTO toDto(TenantDocument tenantDocument);

    @Mapping(source = "uploadedById", target = "uploadedBy")
    @Mapping(source = "approvedById", target = "approvedBy")
    TenantDocument toEntity(TenantDocumentDTO tenantDocumentDTO);

    default TenantDocument fromId(Long id) {
        if (id == null) {
            return null;
        }
        TenantDocument tenantDocument = new TenantDocument();
        tenantDocument.setId(id);
        return tenantDocument;
    }
}
