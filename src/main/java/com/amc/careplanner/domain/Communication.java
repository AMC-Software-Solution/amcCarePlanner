package com.amc.careplanner.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

import com.amc.careplanner.domain.enumeration.CommunicationType;

/**
 * A Communication.
 */
@Entity
@Table(name = "communication")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Communication implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "communication_type", nullable = false)
    private CommunicationType communicationType;

    @Column(name = "note")
    private String note;

    @NotNull
    @Column(name = "communication_date", nullable = false)
    private ZonedDateTime communicationDate;

    @Lob
    @Column(name = "attachment")
    private byte[] attachment;

    @Column(name = "attachment_content_type")
    private String attachmentContentType;

    @Column(name = "attachment_url")
    private String attachmentUrl;

    @Column(name = "last_updated_date")
    private ZonedDateTime lastUpdatedDate;

    @NotNull
    @Column(name = "tenant_id", nullable = false)
    private Long tenantId;

    @ManyToOne
    @JsonIgnoreProperties(value = "communications", allowSetters = true)
    private ServiceUser serviceUser;

    @ManyToOne
    @JsonIgnoreProperties(value = "communications", allowSetters = true)
    private Employee communicatedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CommunicationType getCommunicationType() {
        return communicationType;
    }

    public Communication communicationType(CommunicationType communicationType) {
        this.communicationType = communicationType;
        return this;
    }

    public void setCommunicationType(CommunicationType communicationType) {
        this.communicationType = communicationType;
    }

    public String getNote() {
        return note;
    }

    public Communication note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public ZonedDateTime getCommunicationDate() {
        return communicationDate;
    }

    public Communication communicationDate(ZonedDateTime communicationDate) {
        this.communicationDate = communicationDate;
        return this;
    }

    public void setCommunicationDate(ZonedDateTime communicationDate) {
        this.communicationDate = communicationDate;
    }

    public byte[] getAttachment() {
        return attachment;
    }

    public Communication attachment(byte[] attachment) {
        this.attachment = attachment;
        return this;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }

    public String getAttachmentContentType() {
        return attachmentContentType;
    }

    public Communication attachmentContentType(String attachmentContentType) {
        this.attachmentContentType = attachmentContentType;
        return this;
    }

    public void setAttachmentContentType(String attachmentContentType) {
        this.attachmentContentType = attachmentContentType;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public Communication attachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
        return this;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    public ZonedDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public Communication lastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
        return this;
    }

    public void setLastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public Communication tenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public ServiceUser getServiceUser() {
        return serviceUser;
    }

    public Communication serviceUser(ServiceUser serviceUser) {
        this.serviceUser = serviceUser;
        return this;
    }

    public void setServiceUser(ServiceUser serviceUser) {
        this.serviceUser = serviceUser;
    }

    public Employee getCommunicatedBy() {
        return communicatedBy;
    }

    public Communication communicatedBy(Employee employee) {
        this.communicatedBy = employee;
        return this;
    }

    public void setCommunicatedBy(Employee employee) {
        this.communicatedBy = employee;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Communication)) {
            return false;
        }
        return id != null && id.equals(((Communication) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Communication{" +
            "id=" + getId() +
            ", communicationType='" + getCommunicationType() + "'" +
            ", note='" + getNote() + "'" +
            ", communicationDate='" + getCommunicationDate() + "'" +
            ", attachment='" + getAttachment() + "'" +
            ", attachmentContentType='" + getAttachmentContentType() + "'" +
            ", attachmentUrl='" + getAttachmentUrl() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", tenantId=" + getTenantId() +
            "}";
    }
}
