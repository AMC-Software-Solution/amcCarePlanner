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

    private StringFilter documentTypeDescription;

    private ZonedDateTimeFilter createdDate;

    private ZonedDateTimeFilter lastUpdatedDate;

    private BooleanFilter hasExtraData;

    public DocumentTypeCriteria() {
    }

    public DocumentTypeCriteria(DocumentTypeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.documentTypeTitle = other.documentTypeTitle == null ? null : other.documentTypeTitle.copy();
        this.documentTypeDescription = other.documentTypeDescription == null ? null : other.documentTypeDescription.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.lastUpdatedDate = other.lastUpdatedDate == null ? null : other.lastUpdatedDate.copy();
        this.hasExtraData = other.hasExtraData == null ? null : other.hasExtraData.copy();
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

    public StringFilter getDocumentTypeDescription() {
        return documentTypeDescription;
    }

    public void setDocumentTypeDescription(StringFilter documentTypeDescription) {
        this.documentTypeDescription = documentTypeDescription;
    }

    public ZonedDateTimeFilter getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTimeFilter createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTimeFilter getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(ZonedDateTimeFilter lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public BooleanFilter getHasExtraData() {
        return hasExtraData;
    }

    public void setHasExtraData(BooleanFilter hasExtraData) {
        this.hasExtraData = hasExtraData;
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
            Objects.equals(documentTypeDescription, that.documentTypeDescription) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastUpdatedDate, that.lastUpdatedDate) &&
            Objects.equals(hasExtraData, that.hasExtraData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        documentTypeTitle,
        documentTypeDescription,
        createdDate,
        lastUpdatedDate,
        hasExtraData
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentTypeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (documentTypeTitle != null ? "documentTypeTitle=" + documentTypeTitle + ", " : "") +
                (documentTypeDescription != null ? "documentTypeDescription=" + documentTypeDescription + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastUpdatedDate != null ? "lastUpdatedDate=" + lastUpdatedDate + ", " : "") +
                (hasExtraData != null ? "hasExtraData=" + hasExtraData + ", " : "") +
            "}";
    }

}
