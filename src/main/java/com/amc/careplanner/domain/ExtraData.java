package com.amc.careplanner.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

import com.amc.careplanner.domain.enumeration.DataType;

/**
 * A ExtraData.
 */
@Entity
@Table(name = "extra_data")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ExtraData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "entity_name", nullable = false)
    private String entityName;

    @NotNull
    @Column(name = "entity_id", nullable = false)
    private Long entityId;

    @Column(name = "extra_data_key")
    private String extraDataKey;

    @Column(name = "extra_data_value")
    private String extraDataValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "extra_data_value_data_type")
    private DataType extraDataValueDataType;

    @Column(name = "extra_data_description")
    private String extraDataDescription;

    @Column(name = "extra_data_date")
    private ZonedDateTime extraDataDate;

    @Column(name = "has_extra_data")
    private Boolean hasExtraData;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntityName() {
        return entityName;
    }

    public ExtraData entityName(String entityName) {
        this.entityName = entityName;
        return this;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public Long getEntityId() {
        return entityId;
    }

    public ExtraData entityId(Long entityId) {
        this.entityId = entityId;
        return this;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getExtraDataKey() {
        return extraDataKey;
    }

    public ExtraData extraDataKey(String extraDataKey) {
        this.extraDataKey = extraDataKey;
        return this;
    }

    public void setExtraDataKey(String extraDataKey) {
        this.extraDataKey = extraDataKey;
    }

    public String getExtraDataValue() {
        return extraDataValue;
    }

    public ExtraData extraDataValue(String extraDataValue) {
        this.extraDataValue = extraDataValue;
        return this;
    }

    public void setExtraDataValue(String extraDataValue) {
        this.extraDataValue = extraDataValue;
    }

    public DataType getExtraDataValueDataType() {
        return extraDataValueDataType;
    }

    public ExtraData extraDataValueDataType(DataType extraDataValueDataType) {
        this.extraDataValueDataType = extraDataValueDataType;
        return this;
    }

    public void setExtraDataValueDataType(DataType extraDataValueDataType) {
        this.extraDataValueDataType = extraDataValueDataType;
    }

    public String getExtraDataDescription() {
        return extraDataDescription;
    }

    public ExtraData extraDataDescription(String extraDataDescription) {
        this.extraDataDescription = extraDataDescription;
        return this;
    }

    public void setExtraDataDescription(String extraDataDescription) {
        this.extraDataDescription = extraDataDescription;
    }

    public ZonedDateTime getExtraDataDate() {
        return extraDataDate;
    }

    public ExtraData extraDataDate(ZonedDateTime extraDataDate) {
        this.extraDataDate = extraDataDate;
        return this;
    }

    public void setExtraDataDate(ZonedDateTime extraDataDate) {
        this.extraDataDate = extraDataDate;
    }

    public Boolean isHasExtraData() {
        return hasExtraData;
    }

    public ExtraData hasExtraData(Boolean hasExtraData) {
        this.hasExtraData = hasExtraData;
        return this;
    }

    public void setHasExtraData(Boolean hasExtraData) {
        this.hasExtraData = hasExtraData;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExtraData)) {
            return false;
        }
        return id != null && id.equals(((ExtraData) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExtraData{" +
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
