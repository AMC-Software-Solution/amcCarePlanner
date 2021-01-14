package com.amc.careplanner.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.amc.careplanner.domain.EmployeeDesignation} entity.
 */
public class EmployeeDesignationDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String designation;

    private String description;

    private String designationDate;

    @NotNull
    private Long clientId;

    private Boolean hasExtraData;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDesignationDate() {
        return designationDate;
    }

    public void setDesignationDate(String designationDate) {
        this.designationDate = designationDate;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
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
        if (!(o instanceof EmployeeDesignationDTO)) {
            return false;
        }

        return id != null && id.equals(((EmployeeDesignationDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeDesignationDTO{" +
            "id=" + getId() +
            ", designation='" + getDesignation() + "'" +
            ", description='" + getDescription() + "'" +
            ", designationDate='" + getDesignationDate() + "'" +
            ", clientId=" + getClientId() +
            ", hasExtraData='" + isHasExtraData() + "'" +
            "}";
    }
}
