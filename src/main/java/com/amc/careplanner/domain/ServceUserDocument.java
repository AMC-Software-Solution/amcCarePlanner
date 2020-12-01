package com.amc.careplanner.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

import com.amc.careplanner.domain.enumeration.DocumentStatus;

/**
 * A ServceUserDocument.
 */
@Entity
@Table(name = "servce_user_document")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ServceUserDocument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "document_name", nullable = false)
    private String documentName;

    @Column(name = "document_number")
    private String documentNumber;

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
    @JsonIgnoreProperties(value = "servceUserDocuments", allowSetters = true)
    private ServiceUser owner;

    @ManyToOne
    @JsonIgnoreProperties(value = "servceUserDocuments", allowSetters = true)
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

    public ServceUserDocument documentName(String documentName) {
        this.documentName = documentName;
        return this;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public ServceUserDocument documentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
        return this;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public DocumentStatus getDocumentStatus() {
        return documentStatus;
    }

    public ServceUserDocument documentStatus(DocumentStatus documentStatus) {
        this.documentStatus = documentStatus;
        return this;
    }

    public void setDocumentStatus(DocumentStatus documentStatus) {
        this.documentStatus = documentStatus;
    }

    public String getNote() {
        return note;
    }

    public ServceUserDocument note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDate getIssuedDate() {
        return issuedDate;
    }

    public ServceUserDocument issuedDate(LocalDate issuedDate) {
        this.issuedDate = issuedDate;
        return this;
    }

    public void setIssuedDate(LocalDate issuedDate) {
        this.issuedDate = issuedDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public ServceUserDocument expiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public ZonedDateTime getUploadedDate() {
        return uploadedDate;
    }

    public ServceUserDocument uploadedDate(ZonedDateTime uploadedDate) {
        this.uploadedDate = uploadedDate;
        return this;
    }

    public void setUploadedDate(ZonedDateTime uploadedDate) {
        this.uploadedDate = uploadedDate;
    }

    public byte[] getDocumentFile() {
        return documentFile;
    }

    public ServceUserDocument documentFile(byte[] documentFile) {
        this.documentFile = documentFile;
        return this;
    }

    public void setDocumentFile(byte[] documentFile) {
        this.documentFile = documentFile;
    }

    public String getDocumentFileContentType() {
        return documentFileContentType;
    }

    public ServceUserDocument documentFileContentType(String documentFileContentType) {
        this.documentFileContentType = documentFileContentType;
        return this;
    }

    public void setDocumentFileContentType(String documentFileContentType) {
        this.documentFileContentType = documentFileContentType;
    }

    public String getDocumentFileUrl() {
        return documentFileUrl;
    }

    public ServceUserDocument documentFileUrl(String documentFileUrl) {
        this.documentFileUrl = documentFileUrl;
        return this;
    }

    public void setDocumentFileUrl(String documentFileUrl) {
        this.documentFileUrl = documentFileUrl;
    }

    public ZonedDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public ServceUserDocument lastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
        return this;
    }

    public void setLastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Long getClientId() {
        return clientId;
    }

    public ServceUserDocument clientId(Long clientId) {
        this.clientId = clientId;
        return this;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public ServiceUser getOwner() {
        return owner;
    }

    public ServceUserDocument owner(ServiceUser serviceUser) {
        this.owner = serviceUser;
        return this;
    }

    public void setOwner(ServiceUser serviceUser) {
        this.owner = serviceUser;
    }

    public Employee getApprovedBy() {
        return approvedBy;
    }

    public ServceUserDocument approvedBy(Employee employee) {
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
        if (!(o instanceof ServceUserDocument)) {
            return false;
        }
        return id != null && id.equals(((ServceUserDocument) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServceUserDocument{" +
            "id=" + getId() +
            ", documentName='" + getDocumentName() + "'" +
            ", documentNumber='" + getDocumentNumber() + "'" +
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
