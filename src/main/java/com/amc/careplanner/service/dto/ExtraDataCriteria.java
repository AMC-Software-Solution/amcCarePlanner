package com.amc.careplanner.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.amc.careplanner.domain.enumeration.DataType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.amc.careplanner.domain.ExtraData} entity. This class is used
 * in {@link com.amc.careplanner.web.rest.ExtraDataResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /extra-data?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ExtraDataCriteria implements Serializable, Criteria {
    /**
     * Class for filtering DataType
     */
    public static class DataTypeFilter extends Filter<DataType> {

        public DataTypeFilter() {
        }

        public DataTypeFilter(DataTypeFilter filter) {
            super(filter);
        }

        @Override
        public DataTypeFilter copy() {
            return new DataTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter entityName;

    private LongFilter entityId;

    private StringFilter extraDataKey;

    private StringFilter extraDataValue;

    private DataTypeFilter extraDataValueDataType;

    private StringFilter extraDataDescription;

    private ZonedDateTimeFilter extraDataDate;

    private BooleanFilter hasExtraData;

    public ExtraDataCriteria() {
    }

    public ExtraDataCriteria(ExtraDataCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.entityName = other.entityName == null ? null : other.entityName.copy();
        this.entityId = other.entityId == null ? null : other.entityId.copy();
        this.extraDataKey = other.extraDataKey == null ? null : other.extraDataKey.copy();
        this.extraDataValue = other.extraDataValue == null ? null : other.extraDataValue.copy();
        this.extraDataValueDataType = other.extraDataValueDataType == null ? null : other.extraDataValueDataType.copy();
        this.extraDataDescription = other.extraDataDescription == null ? null : other.extraDataDescription.copy();
        this.extraDataDate = other.extraDataDate == null ? null : other.extraDataDate.copy();
        this.hasExtraData = other.hasExtraData == null ? null : other.hasExtraData.copy();
    }

    @Override
    public ExtraDataCriteria copy() {
        return new ExtraDataCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getEntityName() {
        return entityName;
    }

    public void setEntityName(StringFilter entityName) {
        this.entityName = entityName;
    }

    public LongFilter getEntityId() {
        return entityId;
    }

    public void setEntityId(LongFilter entityId) {
        this.entityId = entityId;
    }

    public StringFilter getExtraDataKey() {
        return extraDataKey;
    }

    public void setExtraDataKey(StringFilter extraDataKey) {
        this.extraDataKey = extraDataKey;
    }

    public StringFilter getExtraDataValue() {
        return extraDataValue;
    }

    public void setExtraDataValue(StringFilter extraDataValue) {
        this.extraDataValue = extraDataValue;
    }

    public DataTypeFilter getExtraDataValueDataType() {
        return extraDataValueDataType;
    }

    public void setExtraDataValueDataType(DataTypeFilter extraDataValueDataType) {
        this.extraDataValueDataType = extraDataValueDataType;
    }

    public StringFilter getExtraDataDescription() {
        return extraDataDescription;
    }

    public void setExtraDataDescription(StringFilter extraDataDescription) {
        this.extraDataDescription = extraDataDescription;
    }

    public ZonedDateTimeFilter getExtraDataDate() {
        return extraDataDate;
    }

    public void setExtraDataDate(ZonedDateTimeFilter extraDataDate) {
        this.extraDataDate = extraDataDate;
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
        final ExtraDataCriteria that = (ExtraDataCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(entityName, that.entityName) &&
            Objects.equals(entityId, that.entityId) &&
            Objects.equals(extraDataKey, that.extraDataKey) &&
            Objects.equals(extraDataValue, that.extraDataValue) &&
            Objects.equals(extraDataValueDataType, that.extraDataValueDataType) &&
            Objects.equals(extraDataDescription, that.extraDataDescription) &&
            Objects.equals(extraDataDate, that.extraDataDate) &&
            Objects.equals(hasExtraData, that.hasExtraData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        entityName,
        entityId,
        extraDataKey,
        extraDataValue,
        extraDataValueDataType,
        extraDataDescription,
        extraDataDate,
        hasExtraData
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExtraDataCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (entityName != null ? "entityName=" + entityName + ", " : "") +
                (entityId != null ? "entityId=" + entityId + ", " : "") +
                (extraDataKey != null ? "extraDataKey=" + extraDataKey + ", " : "") +
                (extraDataValue != null ? "extraDataValue=" + extraDataValue + ", " : "") +
                (extraDataValueDataType != null ? "extraDataValueDataType=" + extraDataValueDataType + ", " : "") +
                (extraDataDescription != null ? "extraDataDescription=" + extraDataDescription + ", " : "") +
                (extraDataDate != null ? "extraDataDate=" + extraDataDate + ", " : "") +
                (hasExtraData != null ? "hasExtraData=" + hasExtraData + ", " : "") +
            "}";
    }

}
