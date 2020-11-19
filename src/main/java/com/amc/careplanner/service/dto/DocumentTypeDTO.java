package com.amc.careplanner.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.amc.careplanner.domain.DocumentType} entity.
 */
public class DocumentTypeDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String documentTypeTitle;

    @NotNull
    private String documentTypeCode;

    private String documentTypeDescription;

    private ZonedDateTime lastUpdatedDate;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentTypeTitle() {
        return documentTypeTitle;
    }

    public void setDocumentTypeTitle(String documentTypeTitle) {
        this.documentTypeTitle = documentTypeTitle;
    }

    public String getDocumentTypeCode() {
        return documentTypeCode;
    }

    public void setDocumentTypeCode(String documentTypeCode) {
        this.documentTypeCode = documentTypeCode;
    }

    public String getDocumentTypeDescription() {
        return documentTypeDescription;
    }

    public void setDocumentTypeDescription(String documentTypeDescription) {
        this.documentTypeDescription = documentTypeDescription;
    }

    public ZonedDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumentTypeDTO)) {
            return false;
        }

        return id != null && id.equals(((DocumentTypeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentTypeDTO{" +
            "id=" + getId() +
            ", documentTypeTitle='" + getDocumentTypeTitle() + "'" +
            ", documentTypeCode='" + getDocumentTypeCode() + "'" +
            ", documentTypeDescription='" + getDocumentTypeDescription() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            "}";
    }
}
