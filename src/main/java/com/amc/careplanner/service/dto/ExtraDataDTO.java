package com.amc.careplanner.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import com.amc.careplanner.domain.enumeration.DataType;

/**
 * A DTO for the {@link com.amc.careplanner.domain.ExtraData} entity.
 */
public class ExtraDataDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String entityName;

    @NotNull
    private Long entityId;

    private String extraDataKey;

    private String extraDataValue;

    private DataType extraDataValueDataType;

    private String extraDataDescription;

    private ZonedDateTime extraDataDate;

    private Boolean hasExtraData;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getExtraDataKey() {
        return extraDataKey;
    }

    public void setExtraDataKey(String extraDataKey) {
        this.extraDataKey = extraDataKey;
    }

    public String getExtraDataValue() {
        return extraDataValue;
    }

    public void setExtraDataValue(String extraDataValue) {
        this.extraDataValue = extraDataValue;
    }

    public DataType getExtraDataValueDataType() {
        return extraDataValueDataType;
    }

    public void setExtraDataValueDataType(DataType extraDataValueDataType) {
        this.extraDataValueDataType = extraDataValueDataType;
    }

    public String getExtraDataDescription() {
        return extraDataDescription;
    }

    public void setExtraDataDescription(String extraDataDescription) {
        this.extraDataDescription = extraDataDescription;
    }

    public ZonedDateTime getExtraDataDate() {
        return extraDataDate;
    }

    public void setExtraDataDate(ZonedDateTime extraDataDate) {
        this.extraDataDate = extraDataDate;
    }

    public Boolean isHasExtraData() {
        return hasExtraData;
    }

    public void setHasExtraData(Boolean hasExtraData) {
        this.hasExtraData = hasExtraData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExtraDataDTO)) {
            return false;
        }

        return id != null && id.equals(((ExtraDataDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExtraDataDTO{" +
            "id=" + getId() +
            ", entityName='" + getEntityName() + "'" +
            ", entityId=" + getEntityId() +
            ", extraDataKey='" + getExtraDataKey() + "'" +
            ", extraDataValue='" + getExtraDataValue() + "'" +
            ", extraDataValueDataType='" + getExtraDataValueDataType() + "'" +
            ", extraDataDescription='" + getExtraDataDescription() + "'" +
            ", extraDataDate='" + getExtraDataDate() + "'" +
            ", hasExtraData='" + isHasExtraData() + "'" +
            "}";
    }
}
