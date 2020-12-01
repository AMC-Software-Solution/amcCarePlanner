package com.amc.careplanner.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.amc.careplanner.domain.SystemSetting} entity.
 */
public class SystemSettingDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String fieldName;

    @NotNull
    private String fieldValue;

    @NotNull
    private String defaultValue;

    private Boolean settingEnabled;

    private ZonedDateTime createdDate;

    private ZonedDateTime updatedDate;

    private Long clientId;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Boolean isSettingEnabled() {
        return settingEnabled;
    }

    public void setSettingEnabled(Boolean settingEnabled) {
        this.settingEnabled = settingEnabled;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SystemSettingDTO)) {
            return false;
        }

        return id != null && id.equals(((SystemSettingDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SystemSettingDTO{" +
            "id=" + getId() +
            ", fieldName='" + getFieldName() + "'" +
            ", fieldValue='" + getFieldValue() + "'" +
            ", defaultValue='" + getDefaultValue() + "'" +
            ", settingEnabled='" + isSettingEnabled() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", clientId=" + getClientId() +
            "}";
    }
}
