package com.amc.careplanner.service.mapper;


import com.amc.careplanner.domain.*;
import com.amc.careplanner.service.dto.DocumentTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DocumentType} and its DTO {@link DocumentTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DocumentTypeMapper extends EntityMapper<DocumentTypeDTO, DocumentType> {



    default DocumentType fromId(Long id) {
        if (id == null) {
            return null;
        }
        DocumentType documentType = new DocumentType();
        documentType.setId(id);
        return documentType;
    }
}
