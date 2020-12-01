package com.amc.careplanner.service.mapper;


import com.amc.careplanner.domain.*;
import com.amc.careplanner.service.dto.EmployeeDocumentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmployeeDocument} and its DTO {@link EmployeeDocumentDTO}.
 */
@Mapper(componentModel = "spring", uses = {DocumentTypeMapper.class, EmployeeMapper.class})
public interface EmployeeDocumentMapper extends EntityMapper<EmployeeDocumentDTO, EmployeeDocument> {

    @Mapping(source = "documentType.id", target = "documentTypeId")
    @Mapping(source = "documentType.documentTypeTitle", target = "documentTypeDocumentTypeTitle")
    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "employee.employeeCode", target = "employeeEmployeeCode")
    @Mapping(source = "approvedBy.id", target = "approvedById")
    @Mapping(source = "approvedBy.employeeCode", target = "approvedByEmployeeCode")
    EmployeeDocumentDTO toDto(EmployeeDocument employeeDocument);

    @Mapping(source = "documentTypeId", target = "documentType")
    @Mapping(source = "employeeId", target = "employee")
    @Mapping(source = "approvedById", target = "approvedBy")
    EmployeeDocument toEntity(EmployeeDocumentDTO employeeDocumentDTO);

    default EmployeeDocument fromId(Long id) {
        if (id == null) {
            return null;
        }
        EmployeeDocument employeeDocument = new EmployeeDocument();
        employeeDocument.setId(id);
        return employeeDocument;
    }
}
