package com.amc.careplanner.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;
import com.amc.careplanner.domain.enumeration.CommunicationType;

/**
 * A DTO for the {@link com.amc.careplanner.domain.Communication} entity.
 */
public class CommunicationDTO implements Serializable {
    
    private Long id;

    @NotNull
    private CommunicationType communicationType;

    private String note;

    @NotNull
    private ZonedDateTime communicationDate;

    @Lob
    private byte[] attachment;

    private String attachmentContentType;
    private String attachmentUrl;

    private ZonedDateTime lastUpdatedDate;

    @NotNull
    private Long tenantId;


    private Long serviceUserId;

    private String serviceUserServiceUserCode;

    private Long communicatedById;

    private String communicatedByEmployeeCode;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CommunicationType getCommunicationType() {
        return communicationType;
    }

    public void setCommunicationType(CommunicationType communicationType) {
        this.communicationType = communicationType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public ZonedDateTime getCommunicationDate() {
        return communicationDate;
    }

    public void setCommunicationDate(ZonedDateTime communicationDate) {
        this.communicationDate = communicationDate;
    }

    public byte[] getAttachment() {
        return attachment;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }

    public String getAttachmentContentType() {
        return attachmentContentType;
    }

    public void setAttachmentContentType(String attachmentContentType) {
        this.attachmentContentType = attachmentContentType;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    public ZonedDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getServiceUserId() {
        return serviceUserId;
    }

    public void setServiceUserId(Long serviceUserId) {
        this.serviceUserId = serviceUserId;
    }

    public String getServiceUserServiceUserCode() {
        return serviceUserServiceUserCode;
    }

    public void setServiceUserServiceUserCode(String serviceUserServiceUserCode) {
        this.serviceUserServiceUserCode = serviceUserServiceUserCode;
    }

    public Long getCommunicatedById() {
        return communicatedById;
    }

    public void setCommunicatedById(Long employeeId) {
        this.communicatedById = employeeId;
    }

    public String getCommunicatedByEmployeeCode() {
        return communicatedByEmployeeCode;
    }

    public void setCommunicatedByEmployeeCode(String employeeEmployeeCode) {
        this.communicatedByEmployeeCode = employeeEmployeeCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommunicationDTO)) {
            return false;
        }

        return id != null && id.equals(((CommunicationDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommunicationDTO{" +
            "id=" + getId() +
            ", communicationType='" + getCommunicationType() + "'" +
            ", note='" + getNote() + "'" +
            ", communicationDate='" + getCommunicationDate() + "'" +
            ", attachment='" + getAttachment() + "'" +
            ", attachmentUrl='" + getAttachmentUrl() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", tenantId=" + getTenantId() +
            ", serviceUserId=" + getServiceUserId() +
            ", serviceUserServiceUserCode='" + getServiceUserServiceUserCode() + "'" +
            ", communicatedById=" + getCommunicatedById() +
            ", communicatedByEmployeeCode='" + getCommunicatedByEmployeeCode() + "'" +
            "}";
    }
}
