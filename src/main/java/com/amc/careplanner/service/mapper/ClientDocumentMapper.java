package com.amc.careplanner.service.mapper;


import com.amc.careplanner.domain.*;
import com.amc.careplanner.service.dto.ClientDocumentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ClientDocument} and its DTO {@link ClientDocumentDTO}.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface ClientDocumentMapper extends EntityMapper<ClientDocumentDTO, ClientDocument> {

    @Mapping(source = "uploadedBy.id", target = "uploadedById")
    @Mapping(source = "uploadedBy.employeeCode", target = "uploadedByEmployeeCode")
    @Mapping(source = "approvedBy.id", target = "approvedById")
    @Mapping(source = "approvedBy.employeeCode", target = "approvedByEmployeeCode")
    ClientDocumentDTO toDto(ClientDocument clientDocument);

    @Mapping(source = "uploadedById", target = "uploadedBy")
    @Mapping(source = "approvedById", target = "approvedBy")
    ClientDocument toEntity(ClientDocumentDTO clientDocumentDTO);

    default ClientDocument fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClientDocument clientDocument = new ClientDocument();
        clientDocument.setId(id);
        return clientDocument;
    }
}
