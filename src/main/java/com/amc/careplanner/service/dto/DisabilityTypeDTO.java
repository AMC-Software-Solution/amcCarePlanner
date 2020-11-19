package com.amc.careplanner.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.amc.careplanner.domain.DisabilityType} entity.
 */
public class DisabilityTypeDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String disability;

    private String description;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisability() {
        return disability;
    }

    public void setDisability(String disability) {
        this.disability = disability;
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
        if (!(o instanceof DisabilityTypeDTO)) {
            return false;
        }

        return id != null && id.equals(((DisabilityTypeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DisabilityTypeDTO{" +
            "id=" + getId() +
            ", disability='" + getDisability() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
