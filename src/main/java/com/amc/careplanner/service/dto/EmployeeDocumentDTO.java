package com.amc.careplanner.service.dto;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;
import com.amc.careplanner.domain.enumeration.DocumentStatus;

/**
 * A DTO for the {@link com.amc.careplanner.domain.EmployeeDocument} entity.
 */
public class EmployeeDocumentDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String documentName;

    private String documentNumber;

    private DocumentStatus documentStatus;

    private String note;

    private LocalDate issuedDate;

    private LocalDate expiryDate;

    private ZonedDateTime uploadedDate;

    @Lob
    private byte[] documentFile;

    private String documentFileContentType;
    private String documentFileUrl;

    private ZonedDateTime lastUpdatedDate;

    @NotNull
    private Long clientId;


    private Long documentTypeId;

    private String documentTypeDocumentTypeTitle;

    private Long employeeId;

    private String employeeEmployeeCode;

    private Long approvedById;

    private String approvedByEmployeeCode;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public DocumentStatus getDocumentStatus() {
        return documentStatus;
    }

    public void setDocumentStatus(DocumentStatus documentStatus) {
        this.documentStatus = documentStatus;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDate getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(LocalDate issuedDate) {
        this.issuedDate = issuedDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public ZonedDateTime getUploadedDate() {
        return uploadedDate;
    }

    public void setUploadedDate(ZonedDateTime uploadedDate) {
        this.uploadedDate = uploadedDate;
    }

    public byte[] getDocumentFile() {
        return documentFile;
    }

    public void setDocumentFile(byte[] documentFile) {
        this.documentFile = documentFile;
    }

    public String getDocumentFileContentType() {
        return documentFileContentType;
    }

    public void setDocumentFileContentType(String documentFileContentType) {
        this.documentFileContentType = documentFileContentType;
    }

    public String getDocumentFileUrl() {
        return documentFileUrl;
    }

    public void setDocumentFileUrl(String documentFileUrl) {
        this.documentFileUrl = documentFileUrl;
    }

    public ZonedDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(Long documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    public String getDocumentTypeDocumentTypeTitle() {
        return documentTypeDocumentTypeTitle;
    }

    public void setDocumentTypeDocumentTypeTitle(String documentTypeDocumentTypeTitle) {
        this.documentTypeDocumentTypeTitle = documentTypeDocumentTypeTitle;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeEmployeeCode() {
        return employeeEmployeeCode;
    }

    public void setEmployeeEmployeeCode(String employeeEmployeeCode) {
        this.employeeEmployeeCode = employeeEmployeeCode;
    }

    public Long getApprovedById() {
        return approvedById;
    }

    public void setApprovedById(Long employeeId) {
        this.approvedById = employeeId;
    }

    public String getApprovedByEmployeeCode() {
        return approvedByEmployeeCode;
    }

    public void setApprovedByEmployeeCode(String employeeEmployeeCode) {
        this.approvedByEmployeeCode = employeeEmployeeCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeDocumentDTO)) {
            return false;
        }

        return id != null && id.equals(((EmployeeDocumentDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeDocumentDTO{" +
            "id=" + getId() +
            ", documentName='" + getDocumentName() + "'" +
            ", documentNumber='" + getDocumentNumber() + "'" +
            ", documentStatus='" + getDocumentStatus() + "'" +
            ", note='" + getNote() + "'" +
            ", issuedDate='" + getIssuedDate() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", uploadedDate='" + getUploadedDate() + "'" +
            ", documentFile='" + getDocumentFile() + "'" +
            ", documentFileUrl='" + getDocumentFileUrl() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", clientId=" + getClientId() +
            ", documentTypeId=" + getDocumentTypeId() +
            ", documentTypeDocumentTypeTitle='" + getDocumentTypeDocumentTypeTitle() + "'" +
            ", employeeId=" + getEmployeeId() +
            ", employeeEmployeeCode='" + getEmployeeEmployeeCode() + "'" +
            ", approvedById=" + getApprovedById() +
            ", approvedByEmployeeCode='" + getApprovedByEmployeeCode() + "'" +
            "}";
    }
}
