package com.amc.careplanner.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.amc.careplanner.domain.EligibilityType} entity.
 */
public class EligibilityTypeDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String eligibilityType;

    private String description;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEligibilityType() {
        return eligibilityType;
    }

    public void setEligibilityType(String eligibilityType) {
        this.eligibilityType = eligibilityType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EligibilityTypeDTO)) {
            return false;
        }

        return id != null && id.equals(((EligibilityTypeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EligibilityTypeDTO{" +
            "id=" + getId() +
            ", eligibilityType='" + getEligibilityType() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
