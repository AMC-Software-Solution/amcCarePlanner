package com.amc.careplanner.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.amc.careplanner.domain.DocumentType} entity. This class is used
 * in {@link com.amc.careplanner.web.rest.DocumentTypeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /document-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DocumentTypeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter documentTypeTitle;

    private StringFilter documentTypeCode;

    private StringFilter documentTypeDescription;

    private ZonedDateTimeFilter lastUpdatedDate;

    public DocumentTypeCriteria() {
    }

    public DocumentTypeCriteria(DocumentTypeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.documentTypeTitle = other.documentTypeTitle == null ? null : other.documentTypeTitle.copy();
        this.documentTypeCode = other.documentTypeCode == null ? null : other.documentTypeCode.copy();
        this.documentTypeDescription = other.documentTypeDescription == null ? null : other.documentTypeDescription.copy();
        this.lastUpdatedDate = other.lastUpdatedDate == null ? null : other.lastUpdatedDate.copy();
    }

    @Override
    public DocumentTypeCriteria copy() {
        return new DocumentTypeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDocumentTypeTitle() {
        return documentTypeTitle;
    }

    public void setDocumentTypeTitle(StringFilter documentTypeTitle) {
        this.documentTypeTitle = documentTypeTitle;
    }

    public StringFilter getDocumentTypeCode() {
        return documentTypeCode;
    }

    public void setDocumentTypeCode(StringFilter documentTypeCode) {
        this.documentTypeCode = documentTypeCode;
    }

    public StringFilter getDocumentTypeDescription() {
        return documentTypeDescription;
    }

    public void setDocumentTypeDescription(StringFilter documentTypeDescription) {
        this.documentTypeDescription = documentTypeDescription;
    }

    public ZonedDateTimeFilter getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(ZonedDateTimeFilter lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DocumentTypeCriteria that = (DocumentTypeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(documentTypeTitle, that.documentTypeTitle) &&
            Objects.equals(documentTypeCode, that.documentTypeCode) &&
            Objects.equals(documentTypeDescription, that.documentTypeDescription) &&
            Objects.equals(lastUpdatedDate, that.lastUpdatedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        documentTypeTitle,
        documentTypeCode,
        documentTypeDescription,
        lastUpdatedDate
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentTypeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (documentTypeTitle != null ? "documentTypeTitle=" + documentTypeTitle + ", " : "") +
                (documentTypeCode != null ? "documentTypeCode=" + documentTypeCode + ", " : "") +
                (documentTypeDescription != null ? "documentTypeDescription=" + documentTypeDescription + ", " : "") +
                (lastUpdatedDate != null ? "lastUpdatedDate=" + lastUpdatedDate + ", " : "") +
            "}";
    }

}
