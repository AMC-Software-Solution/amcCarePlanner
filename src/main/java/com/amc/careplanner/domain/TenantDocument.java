package com.amc.careplanner.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

import com.amc.careplanner.domain.enumeration.TenantDocumentType;

import com.amc.careplanner.domain.enumeration.DocumentStatus;

/**
 * A TenantDocument.
 */
@Entity
@Table(name = "tenant_document")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TenantDocument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "document_name", nullable = false)
    private String documentName;

    @NotNull
    @Column(name = "document_code", nullable = false, unique = true)
    private String documentCode;

    @Column(name = "document_number")
    private String documentNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "document_type", nullable = false)
    private TenantDocumentType documentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "document_status")
    private DocumentStatus documentStatus;

    @Column(name = "note")
    private String note;

    @Column(name = "issued_date")
    private LocalDate issuedDate;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "uploaded_date")
    private ZonedDateTime uploadedDate;

    @Lob
    @Column(name = "document_file")
    private byte[] documentFile;

    @Column(name = "document_file_content_type")
    private String documentFileContentType;

    @Column(name = "document_file_url")
    private String documentFileUrl;

    @Column(name = "last_updated_date")
    private ZonedDateTime lastUpdatedDate;

    @NotNull
    @Column(name = "tenant_id", nullable = false)
    private Long tenantId;

    @ManyToOne
    @JsonIgnoreProperties(value = "tenantDocuments", allowSetters = true)
    private Employee uploadedBy;

    @ManyToOne
    @JsonIgnoreProperties(value = "tenantDocuments", allowSetters = true)
    private Employee approvedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentName() {
        return documentName;
    }

    public TenantDocument documentName(String documentName) {
        this.documentName = documentName;
        return this;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentCode() {
        return documentCode;
    }

    public TenantDocument documentCode(String documentCode) {
        this.documentCode = documentCode;
        return this;
    }

    public void setDocumentCode(String documentCode) {
        this.documentCode = documentCode;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public TenantDocument documentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
        return this;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public TenantDocumentType getDocumentType() {
        return documentType;
    }

    public TenantDocument documentType(TenantDocumentType documentType) {
        this.documentType = documentType;
        return this;
    }

    public void setDocumentType(TenantDocumentType documentType) {
        this.documentType = documentType;
    }

    public DocumentStatus getDocumentStatus() {
        return documentStatus;
    }

    public TenantDocument documentStatus(DocumentStatus documentStatus) {
        this.documentStatus = documentStatus;
        return this;
    }

    public void setDocumentStatus(DocumentStatus documentStatus) {
        this.documentStatus = documentStatus;
    }

    public String getNote() {
        return note;
    }

    public TenantDocument note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDate getIssuedDate() {
        return issuedDate;
    }

    public TenantDocument issuedDate(LocalDate issuedDate) {
        this.issuedDate = issuedDate;
        return this;
    }

    public void setIssuedDate(LocalDate issuedDate) {
        this.issuedDate = issuedDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public TenantDocument expiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public ZonedDateTime getUploadedDate() {
        return uploadedDate;
    }

    public TenantDocument uploadedDate(ZonedDateTime uploadedDate) {
        this.uploadedDate = uploadedDate;
        return this;
    }

    public void setUploadedDate(ZonedDateTime uploadedDate) {
        this.uploadedDate = uploadedDate;
    }

    public byte[] getDocumentFile() {
        return documentFile;
    }

    public TenantDocument documentFile(byte[] documentFile) {
        this.documentFile = documentFile;
        return this;
    }

    public void setDocumentFile(byte[] documentFile) {
        this.documentFile = documentFile;
    }

    public String getDocumentFileContentType() {
        return documentFileContentType;
    }

    public TenantDocument documentFileContentType(String documentFileContentType) {
        this.documentFileContentType = documentFileContentType;
        return this;
    }

    public void setDocumentFileContentType(String documentFileContentType) {
        this.documentFileContentType = documentFileContentType;
    }

    public String getDocumentFileUrl() {
        return documentFileUrl;
    }

    public TenantDocument documentFileUrl(String documentFileUrl) {
        this.documentFileUrl = documentFileUrl;
        return this;
    }

    public void setDocumentFileUrl(String documentFileUrl) {
        this.documentFileUrl = documentFileUrl;
    }

    public ZonedDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public TenantDocument lastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
        return this;
    }

    public void setLastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public TenantDocument tenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Employee getUploadedBy() {
        return uploadedBy;
    }

    public TenantDocument uploadedBy(Employee employee) {
        this.uploadedBy = employee;
        return this;
    }

    public void setUploadedBy(Employee employee) {
        this.uploadedBy = employee;
    }

    public Employee getApprovedBy() {
        return approvedBy;
    }

    public TenantDocument approvedBy(Employee employee) {
        this.approvedBy = employee;
        return this;
    }

    public void setApprovedBy(Employee employee) {
        this.approvedBy = employee;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TenantDocument)) {
            return false;
        }
        return id != null && id.equals(((TenantDocument) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TenantDocument{" +
            "id=" + getId() +
            ", documentName='" + getDocumentName() + "'" +
            ", documentCode='" + getDocumentCode() + "'" +
            ", documentNumber='" + getDocumentNumber() + "'" +
            ", documentType='" + getDocumentType() + "'" +
            ", documentStatus='" + getDocumentStatus() + "'" +
            ", note='" + getNote() + "'" +
            ", issuedDate='" + getIssuedDate() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", uploadedDate='" + getUploadedDate() + "'" +
            ", documentFile='" + getDocumentFile() + "'" +
            ", documentFileContentType='" + getDocumentFileContentType() + "'" +
            ", documentFileUrl='" + getDocumentFileUrl() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", tenantId=" + getTenantId() +
            "}";
    }
}
