package com.amc.careplanner.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.amc.careplanner.domain.enumeration.DocumentStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.amc.careplanner.domain.ServceUserDocument} entity. This class is used
 * in {@link com.amc.careplanner.web.rest.ServceUserDocumentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /servce-user-documents?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ServceUserDocumentCriteria implements Serializable, Criteria {
    /**
     * Class for filtering DocumentStatus
     */
    public static class DocumentStatusFilter extends Filter<DocumentStatus> {

        public DocumentStatusFilter() {
        }

        public DocumentStatusFilter(DocumentStatusFilter filter) {
            super(filter);
        }

        @Override
        public DocumentStatusFilter copy() {
            return new DocumentStatusFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter documentName;

    private StringFilter documentNumber;

    private DocumentStatusFilter documentStatus;

    private StringFilter note;

    private LocalDateFilter issuedDate;

    private LocalDateFilter expiryDate;

    private ZonedDateTimeFilter uploadedDate;

    private StringFilter documentFileUrl;

    private ZonedDateTimeFilter lastUpdatedDate;

    private LongFilter clientId;

    private LongFilter ownerId;

    private LongFilter approvedById;

    public ServceUserDocumentCriteria() {
    }

    public ServceUserDocumentCriteria(ServceUserDocumentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.documentName = other.documentName == null ? null : other.documentName.copy();
        this.documentNumber = other.documentNumber == null ? null : other.documentNumber.copy();
        this.documentStatus = other.documentStatus == null ? null : other.documentStatus.copy();
        this.note = other.note == null ? null : other.note.copy();
        this.issuedDate = other.issuedDate == null ? null : other.issuedDate.copy();
        this.expiryDate = other.expiryDate == null ? null : other.expiryDate.copy();
        this.uploadedDate = other.uploadedDate == null ? null : other.uploadedDate.copy();
        this.documentFileUrl = other.documentFileUrl == null ? null : other.documentFileUrl.copy();
        this.lastUpdatedDate = other.lastUpdatedDate == null ? null : other.lastUpdatedDate.copy();
        this.clientId = other.clientId == null ? null : other.clientId.copy();
        this.ownerId = other.ownerId == null ? null : other.ownerId.copy();
        this.approvedById = other.approvedById == null ? null : other.approvedById.copy();
    }

    @Override
    public ServceUserDocumentCriteria copy() {
        return new ServceUserDocumentCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDocumentName() {
        return documentName;
    }

    public void setDocumentName(StringFilter documentName) {
        this.documentName = documentName;
    }

    public StringFilter getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(StringFilter documentNumber) {
        this.documentNumber = documentNumber;
    }

    public DocumentStatusFilter getDocumentStatus() {
        return documentStatus;
    }

    public void setDocumentStatus(DocumentStatusFilter documentStatus) {
        this.documentStatus = documentStatus;
    }

    public StringFilter getNote() {
        return note;
    }

    public void setNote(StringFilter note) {
        this.note = note;
    }

    public LocalDateFilter getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(LocalDateFilter issuedDate) {
        this.issuedDate = issuedDate;
    }

    public LocalDateFilter getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateFilter expiryDate) {
        this.expiryDate = expiryDate;
    }

    public ZonedDateTimeFilter getUploadedDate() {
        return uploadedDate;
    }

    public void setUploadedDate(ZonedDateTimeFilter uploadedDate) {
        this.uploadedDate = uploadedDate;
    }

    public StringFilter getDocumentFileUrl() {
        return documentFileUrl;
    }

    public void setDocumentFileUrl(StringFilter documentFileUrl) {
        this.documentFileUrl = documentFileUrl;
    }

    public ZonedDateTimeFilter getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(ZonedDateTimeFilter lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public LongFilter getClientId() {
        return clientId;
    }

    public void setClientId(LongFilter clientId) {
        this.clientId = clientId;
    }

    public LongFilter getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(LongFilter ownerId) {
        this.ownerId = ownerId;
    }

    public LongFilter getApprovedById() {
        return approvedById;
    }

    public void setApprovedById(LongFilter approvedById) {
        this.approvedById = approvedById;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ServceUserDocumentCriteria that = (ServceUserDocumentCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(documentName, that.documentName) &&
            Objects.equals(documentNumber, that.documentNumber) &&
            Objects.equals(documentStatus, that.documentStatus) &&
            Objects.equals(note, that.note) &&
            Objects.equals(issuedDate, that.issuedDate) &&
            Objects.equals(expiryDate, that.expiryDate) &&
            Objects.equals(uploadedDate, that.uploadedDate) &&
            Objects.equals(documentFileUrl, that.documentFileUrl) &&
            Objects.equals(lastUpdatedDate, that.lastUpdatedDate) &&
            Objects.equals(clientId, that.clientId) &&
            Objects.equals(ownerId, that.ownerId) &&
            Objects.equals(approvedById, that.approvedById);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        documentName,
        documentNumber,
        documentStatus,
        note,
        issuedDate,
        expiryDate,
        uploadedDate,
        documentFileUrl,
        lastUpdatedDate,
        clientId,
        ownerId,
        approvedById
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServceUserDocumentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (documentName != null ? "documentName=" + documentName + ", " : "") +
                (documentNumber != null ? "documentNumber=" + documentNumber + ", " : "") +
                (documentStatus != null ? "documentStatus=" + documentStatus + ", " : "") +
                (note != null ? "note=" + note + ", " : "") +
                (issuedDate != null ? "issuedDate=" + issuedDate + ", " : "") +
                (expiryDate != null ? "expiryDate=" + expiryDate + ", " : "") +
                (uploadedDate != null ? "uploadedDate=" + uploadedDate + ", " : "") +
                (documentFileUrl != null ? "documentFileUrl=" + documentFileUrl + ", " : "") +
                (lastUpdatedDate != null ? "lastUpdatedDate=" + lastUpdatedDate + ", " : "") +
                (clientId != null ? "clientId=" + clientId + ", " : "") +
                (ownerId != null ? "ownerId=" + ownerId + ", " : "") +
                (approvedById != null ? "approvedById=" + approvedById + ", " : "") +
            "}";
    }

}
