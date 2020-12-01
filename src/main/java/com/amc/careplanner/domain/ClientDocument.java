package com.amc.careplanner.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

import com.amc.careplanner.domain.enumeration.ClientDocumentType;

import com.amc.careplanner.domain.enumeration.DocumentStatus;

/**
 * A ClientDocument.
 */
@Entity
@Table(name = "client_document")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ClientDocument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "document_name", nullable = false)
    private String documentName;

    @Column(name = "document_number")
    private String documentNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "document_type", nullable = false)
    private ClientDocumentType documentType;

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
    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @ManyToOne
    @JsonIgnoreProperties(value = "clientDocuments", allowSetters = true)
    private Employee uploadedBy;

    @ManyToOne
    @JsonIgnoreProperties(value = "clientDocuments", allowSetters = true)
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

    public ClientDocument documentName(String documentName) {
        this.documentName = documentName;
        return this;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public ClientDocument documentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
        return this;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public ClientDocumentType getDocumentType() {
        return documentType;
    }

    public ClientDocument documentType(ClientDocumentType documentType) {
        this.documentType = documentType;
        return this;
    }

    public void setDocumentType(ClientDocumentType documentType) {
        this.documentType = documentType;
    }

    public DocumentStatus getDocumentStatus() {
        return documentStatus;
    }

    public ClientDocument documentStatus(DocumentStatus documentStatus) {
        this.documentStatus = documentStatus;
        return this;
    }

    public void setDocumentStatus(DocumentStatus documentStatus) {
        this.documentStatus = documentStatus;
    }

    public String getNote() {
        return note;
    }

    public ClientDocument note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDate getIssuedDate() {
        return issuedDate;
    }

    public ClientDocument issuedDate(LocalDate issuedDate) {
        this.issuedDate = issuedDate;
        return this;
    }

    public void setIssuedDate(LocalDate issuedDate) {
        this.issuedDate = issuedDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public ClientDocument expiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public ZonedDateTime getUploadedDate() {
        return uploadedDate;
    }

    public ClientDocument uploadedDate(ZonedDateTime uploadedDate) {
        this.uploadedDate = uploadedDate;
        return this;
    }

    public void setUploadedDate(ZonedDateTime uploadedDate) {
        this.uploadedDate = uploadedDate;
    }

    public byte[] getDocumentFile() {
        return documentFile;
    }

    public ClientDocument documentFile(byte[] documentFile) {
        this.documentFile = documentFile;
        return this;
    }

    public void setDocumentFile(byte[] documentFile) {
        this.documentFile = documentFile;
    }

    public String getDocumentFileContentType() {
        return documentFileContentType;
    }

    public ClientDocument documentFileContentType(String documentFileContentType) {
        this.documentFileContentType = documentFileContentType;
        return this;
    }

    public void setDocumentFileContentType(String documentFileContentType) {
        this.documentFileContentType = documentFileContentType;
    }

    public String getDocumentFileUrl() {
        return documentFileUrl;
    }

    public ClientDocument documentFileUrl(String documentFileUrl) {
        this.documentFileUrl = documentFileUrl;
        return this;
    }

    public void setDocumentFileUrl(String documentFileUrl) {
        this.documentFileUrl = documentFileUrl;
    }

    public ZonedDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public ClientDocument lastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
        return this;
    }

    public void setLastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Long getClientId() {
        return clientId;
    }

    public ClientDocument clientId(Long clientId) {
        this.clientId = clientId;
        return this;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Employee getUploadedBy() {
        return uploadedBy;
    }

    public ClientDocument uploadedBy(Employee employee) {
        this.uploadedBy = employee;
        return this;
    }

    public void setUploadedBy(Employee employee) {
        this.uploadedBy = employee;
    }

    public Employee getApprovedBy() {
        return approvedBy;
    }

    public ClientDocument approvedBy(Employee employee) {
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
        if (!(o instanceof ClientDocument)) {
            return false;
        }
        return id != null && id.equals(((ClientDocument) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClientDocument{" +
            "id=" + getId() +
            ", documentName='" + getDocumentName() + "'" +
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
            ", clientId=" + getClientId() +
            "}";
    }
}
