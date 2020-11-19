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
 * Criteria class for the {@link com.amc.careplanner.domain.EmployeeDocument} entity. This class is used
 * in {@link com.amc.careplanner.web.rest.EmployeeDocumentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employee-documents?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EmployeeDocumentCriteria implements Serializable, Criteria {
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

    private StringFilter documentCode;

    private StringFilter documentNumber;

    private DocumentStatusFilter documentStatus;

    private StringFilter note;

    private LocalDateFilter issuedDate;

    private LocalDateFilter expiryDate;

    private ZonedDateTimeFilter uploadedDate;

    private StringFilter documentFileUrl;

    private ZonedDateTimeFilter lastUpdatedDate;

    private LongFilter tenantId;

    private LongFilter documentTypeId;

    private LongFilter employeeId;

    private LongFilter approvedById;

    public EmployeeDocumentCriteria() {
    }

    public EmployeeDocumentCriteria(EmployeeDocumentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.documentName = other.documentName == null ? null : other.documentName.copy();
        this.documentCode = other.documentCode == null ? null : other.documentCode.copy();
        this.documentNumber = other.documentNumber == null ? null : other.documentNumber.copy();
        this.documentStatus = other.documentStatus == null ? null : other.documentStatus.copy();
        this.note = other.note == null ? null : other.note.copy();
        this.issuedDate = other.issuedDate == null ? null : other.issuedDate.copy();
        this.expiryDate = other.expiryDate == null ? null : other.expiryDate.copy();
        this.uploadedDate = other.uploadedDate == null ? null : other.uploadedDate.copy();
        this.documentFileUrl = other.documentFileUrl == null ? null : other.documentFileUrl.copy();
        this.lastUpdatedDate = other.lastUpdatedDate == null ? null : other.lastUpdatedDate.copy();
        this.tenantId = other.tenantId == null ? null : other.tenantId.copy();
        this.documentTypeId = other.documentTypeId == null ? null : other.documentTypeId.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.approvedById = other.approvedById == null ? null : other.approvedById.copy();
    }

    @Override
    public EmployeeDocumentCriteria copy() {
        return new EmployeeDocumentCriteria(this);
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

    public StringFilter getDocumentCode() {
        return documentCode;
    }

    public void setDocumentCode(StringFilter documentCode) {
        this.documentCode = documentCode;
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

    public LongFilter getTenantId() {
        return tenantId;
    }

    public void setTenantId(LongFilter tenantId) {
        this.tenantId = tenantId;
    }

    public LongFilter getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(LongFilter documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    public LongFilter getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(LongFilter employeeId) {
        this.employeeId = employeeId;
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
        final EmployeeDocumentCriteria that = (EmployeeDocumentCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(documentName, that.documentName) &&
            Objects.equals(documentCode, that.documentCode) &&
            Objects.equals(documentNumber, that.documentNumber) &&
            Objects.equals(documentStatus, that.documentStatus) &&
            Objects.equals(note, that.note) &&
            Objects.equals(issuedDate, that.issuedDate) &&
            Objects.equals(expiryDate, that.expiryDate) &&
            Objects.equals(uploadedDate, that.uploadedDate) &&
            Objects.equals(documentFileUrl, that.documentFileUrl) &&
            Objects.equals(lastUpdatedDate, that.lastUpdatedDate) &&
            Objects.equals(tenantId, that.tenantId) &&
            Objects.equals(documentTypeId, that.documentTypeId) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(approvedById, that.approvedById);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        documentName,
        documentCode,
        documentNumber,
        documentStatus,
        note,
        issuedDate,
        expiryDate,
        uploadedDate,
        documentFileUrl,
        lastUpdatedDate,
        tenantId,
        documentTypeId,
        employeeId,
        approvedById
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeDocumentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (documentName != null ? "documentName=" + documentName + ", " : "") +
                (documentCode != null ? "documentCode=" + documentCode + ", " : "") +
                (documentNumber != null ? "documentNumber=" + documentNumber + ", " : "") +
                (documentStatus != null ? "documentStatus=" + documentStatus + ", " : "") +
                (note != null ? "note=" + note + ", " : "") +
                (issuedDate != null ? "issuedDate=" + issuedDate + ", " : "") +
                (expiryDate != null ? "expiryDate=" + expiryDate + ", " : "") +
                (uploadedDate != null ? "uploadedDate=" + uploadedDate + ", " : "") +
                (documentFileUrl != null ? "documentFileUrl=" + documentFileUrl + ", " : "") +
                (lastUpdatedDate != null ? "lastUpdatedDate=" + lastUpdatedDate + ", " : "") +
                (tenantId != null ? "tenantId=" + tenantId + ", " : "") +
                (documentTypeId != null ? "documentTypeId=" + documentTypeId + ", " : "") +
                (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
                (approvedById != null ? "approvedById=" + approvedById + ", " : "") +
            "}";
    }

}
