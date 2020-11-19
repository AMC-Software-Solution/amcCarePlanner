package com.amc.careplanner.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.amc.careplanner.domain.Disability} entity.
 */
public class DisabilityDTO implements Serializable {
    
    private Long id;

    private String note;

    private ZonedDateTime lastUpdatedDate;

    @NotNull
    private Long tenantId;


    private Long disabilityTypeId;

    private String disabilityTypeDisability;

    private Long employeeId;

    private String employeeEmployeeCode;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public ZonedDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getDisabilityTypeId() {
        return disabilityTypeId;
    }

    public void setDisabilityTypeId(Long disabilityTypeId) {
        this.disabilityTypeId = disabilityTypeId;
    }

    public String getDisabilityTypeDisability() {
        return disabilityTypeDisability;
    }

    public void setDisabilityTypeDisability(String disabilityTypeDisability) {
        this.disabilityTypeDisability = disabilityTypeDisability;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeEmployeeCode() {
        return employeeEmployeeCode;
    }

    public void setEmployeeEmployeeCode(String employeeEmployeeCode) {
        this.employeeEmployeeCode = employeeEmployeeCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DisabilityDTO)) {
            return false;
        }

        return id != null && id.equals(((DisabilityDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DisabilityDTO{" +
            "id=" + getId() +
            ", note='" + getNote() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", tenantId=" + getTenantId() +
            ", disabilityTypeId=" + getDisabilityTypeId() +
            ", disabilityTypeDisability='" + getDisabilityTypeDisability() + "'" +
            ", employeeId=" + getEmployeeId() +
            ", employeeEmployeeCode='" + getEmployeeEmployeeCode() + "'" +
            "}";
    }
}
