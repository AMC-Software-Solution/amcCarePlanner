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
 * Criteria class for the {@link com.amc.careplanner.domain.SystemSetting} entity. This class is used
 * in {@link com.amc.careplanner.web.rest.SystemSettingResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /system-settings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SystemSettingCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter fieldName;

    private StringFilter fieldValue;

    private StringFilter defaultValue;

    private BooleanFilter settingEnabled;

    private ZonedDateTimeFilter createdDate;

    private ZonedDateTimeFilter updatedDate;

    private LongFilter tenantId;

    public SystemSettingCriteria() {
    }

    public SystemSettingCriteria(SystemSettingCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.fieldName = other.fieldName == null ? null : other.fieldName.copy();
        this.fieldValue = other.fieldValue == null ? null : other.fieldValue.copy();
        this.defaultValue = other.defaultValue == null ? null : other.defaultValue.copy();
        this.settingEnabled = other.settingEnabled == null ? null : other.settingEnabled.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.updatedDate = other.updatedDate == null ? null : other.updatedDate.copy();
        this.tenantId = other.tenantId == null ? null : other.tenantId.copy();
    }

    @Override
    public SystemSettingCriteria copy() {
        return new SystemSettingCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getFieldName() {
        return fieldName;
    }

    public void setFieldName(StringFilter fieldName) {
        this.fieldName = fieldName;
    }

    public StringFilter getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(StringFilter fieldValue) {
        this.fieldValue = fieldValue;
    }

    public StringFilter getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(StringFilter defaultValue) {
        this.defaultValue = defaultValue;
    }

    public BooleanFilter getSettingEnabled() {
        return settingEnabled;
    }

    public void setSettingEnabled(BooleanFilter settingEnabled) {
        this.settingEnabled = settingEnabled;
    }

    public ZonedDateTimeFilter getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTimeFilter createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTimeFilter getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(ZonedDateTimeFilter updatedDate) {
        this.updatedDate = updatedDate;
    }

    public LongFilter getTenantId() {
        return tenantId;
    }

    public void setTenantId(LongFilter tenantId) {
        this.tenantId = tenantId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SystemSettingCriteria that = (SystemSettingCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(fieldName, that.fieldName) &&
            Objects.equals(fieldValue, that.fieldValue) &&
            Objects.equals(defaultValue, that.defaultValue) &&
            Objects.equals(settingEnabled, that.settingEnabled) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(updatedDate, that.updatedDate) &&
            Objects.equals(tenantId, that.tenantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        fieldName,
        fieldValue,
        defaultValue,
        settingEnabled,
        createdDate,
        updatedDate,
        tenantId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SystemSettingCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fieldName != null ? "fieldName=" + fieldName + ", " : "") +
                (fieldValue != null ? "fieldValue=" + fieldValue + ", " : "") +
                (defaultValue != null ? "defaultValue=" + defaultValue + ", " : "") +
                (settingEnabled != null ? "settingEnabled=" + settingEnabled + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (updatedDate != null ? "updatedDate=" + updatedDate + ", " : "") +
                (tenantId != null ? "tenantId=" + tenantId + ", " : "") +
            "}";
    }

}
