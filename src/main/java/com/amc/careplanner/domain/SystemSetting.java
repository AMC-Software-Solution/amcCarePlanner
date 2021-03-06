package com.amc.careplanner.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A SystemSetting.
 */
@Entity
@Table(name = "system_setting")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SystemSetting implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "field_name", nullable = false)
    private String fieldName;

    @NotNull
    @Column(name = "field_value", nullable = false)
    private String fieldValue;

    @NotNull
    @Column(name = "default_value", nullable = false)
    private String defaultValue;

    @Column(name = "setting_enabled")
    private Boolean settingEnabled;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "updated_date")
    private ZonedDateTime updatedDate;

    @Column(name = "tenant_id")
    private Long tenantId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFieldName() {
        return fieldName;
    }

    public SystemSetting fieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public SystemSetting fieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
        return this;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public SystemSetting defaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Boolean isSettingEnabled() {
        return settingEnabled;
    }

    public SystemSetting settingEnabled(Boolean settingEnabled) {
        this.settingEnabled = settingEnabled;
        return this;
    }

    public void setSettingEnabled(Boolean settingEnabled) {
        this.settingEnabled = settingEnabled;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public SystemSetting createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getUpdatedDate() {
        return updatedDate;
    }

    public SystemSetting updatedDate(ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public SystemSetting tenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SystemSetting)) {
            return false;
        }
        return id != null && id.equals(((SystemSetting) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SystemSetting{" +
            "id=" + getId() +
            ", fieldName='" + getFieldName() + "'" +
            ", fieldValue='" + getFieldValue() + "'" +
            ", defaultValue='" + getDefaultValue() + "'" +
            ", settingEnabled='" + isSettingEnabled() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", tenantId=" + getTenantId() +
            "}";
    }
}
