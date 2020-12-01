package com.amc.careplanner.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.amc.careplanner.domain.Eligibility} entity.
 */
public class EligibilityDTO implements Serializable {
    
    private Long id;

    private String note;

    private ZonedDateTime lastUpdatedDate;

    @NotNull
    private Long clientId;


    private Long eligibilityTypeId;

    private String eligibilityTypeEligibilityType;

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

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getEligibilityTypeId() {
        return eligibilityTypeId;
    }

    public void setEligibilityTypeId(Long eligibilityTypeId) {
        this.eligibilityTypeId = eligibilityTypeId;
    }

    public String getEligibilityTypeEligibilityType() {
        return eligibilityTypeEligibilityType;
    }

    public void setEligibilityTypeEligibilityType(String eligibilityTypeEligibilityType) {
        this.eligibilityTypeEligibilityType = eligibilityTypeEligibilityType;
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
        if (!(o instanceof EligibilityDTO)) {
            return false;
        }

        return id != null && id.equals(((EligibilityDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EligibilityDTO{" +
            "id=" + getId() +
            ", note='" + getNote() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", clientId=" + getClientId() +
            ", eligibilityTypeId=" + getEligibilityTypeId() +
            ", eligibilityTypeEligibilityType='" + getEligibilityTypeEligibilityType() + "'" +
            ", employeeId=" + getEmployeeId() +
            ", employeeEmployeeCode='" + getEmployeeEmployeeCode() + "'" +
            "}";
    }
}
