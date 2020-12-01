package com.amc.careplanner.service.dto;

import java.io.Serializable;
import com.amc.careplanner.domain.enumeration.Shift;

/**
 * A DTO for the {@link com.amc.careplanner.domain.EmployeeAvailability} entity.
 */
public class EmployeeAvailabilityDTO implements Serializable {
    
    private Long id;

    private Boolean isAvailableForWorkWeekDays;

    private Integer minimumHoursPerWeekWeekDays;

    private Integer maximumHoursPerWeekWeekDays;

    private Boolean isAvailableForWorkWeekEnds;

    private Integer minimumHoursPerWeekWeekEnds;

    private Integer maximumHoursPerWeekWeekEnds;

    private Shift leastPreferredShift;


    private Long employeeId;

    private String employeeEmployeeCode;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIsAvailableForWorkWeekDays() {
        return isAvailableForWorkWeekDays;
    }

    public void setIsAvailableForWorkWeekDays(Boolean isAvailableForWorkWeekDays) {
        this.isAvailableForWorkWeekDays = isAvailableForWorkWeekDays;
    }

    public Integer getMinimumHoursPerWeekWeekDays() {
        return minimumHoursPerWeekWeekDays;
    }

    public void setMinimumHoursPerWeekWeekDays(Integer minimumHoursPerWeekWeekDays) {
        this.minimumHoursPerWeekWeekDays = minimumHoursPerWeekWeekDays;
    }

    public Integer getMaximumHoursPerWeekWeekDays() {
        return maximumHoursPerWeekWeekDays;
    }

    public void setMaximumHoursPerWeekWeekDays(Integer maximumHoursPerWeekWeekDays) {
        this.maximumHoursPerWeekWeekDays = maximumHoursPerWeekWeekDays;
    }

    public Boolean isIsAvailableForWorkWeekEnds() {
        return isAvailableForWorkWeekEnds;
    }

    public void setIsAvailableForWorkWeekEnds(Boolean isAvailableForWorkWeekEnds) {
        this.isAvailableForWorkWeekEnds = isAvailableForWorkWeekEnds;
    }

    public Integer getMinimumHoursPerWeekWeekEnds() {
        return minimumHoursPerWeekWeekEnds;
    }

    public void setMinimumHoursPerWeekWeekEnds(Integer minimumHoursPerWeekWeekEnds) {
        this.minimumHoursPerWeekWeekEnds = minimumHoursPerWeekWeekEnds;
    }

    public Integer getMaximumHoursPerWeekWeekEnds() {
        return maximumHoursPerWeekWeekEnds;
    }

    public void setMaximumHoursPerWeekWeekEnds(Integer maximumHoursPerWeekWeekEnds) {
        this.maximumHoursPerWeekWeekEnds = maximumHoursPerWeekWeekEnds;
    }

    public Shift getLeastPreferredShift() {
        return leastPreferredShift;
    }

    public void setLeastPreferredShift(Shift leastPreferredShift) {
        this.leastPreferredShift = leastPreferredShift;
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
        if (!(o instanceof EmployeeAvailabilityDTO)) {
            return false;
        }

        return id != null && id.equals(((EmployeeAvailabilityDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeAvailabilityDTO{" +
            "id=" + getId() +
            ", isAvailableForWorkWeekDays='" + isIsAvailableForWorkWeekDays() + "'" +
            ", minimumHoursPerWeekWeekDays=" + getMinimumHoursPerWeekWeekDays() +
            ", maximumHoursPerWeekWeekDays=" + getMaximumHoursPerWeekWeekDays() +
            ", isAvailableForWorkWeekEnds='" + isIsAvailableForWorkWeekEnds() + "'" +
            ", minimumHoursPerWeekWeekEnds=" + getMinimumHoursPerWeekWeekEnds() +
            ", maximumHoursPerWeekWeekEnds=" + getMaximumHoursPerWeekWeekEnds() +
            ", leastPreferredShift='" + getLeastPreferredShift() + "'" +
            ", employeeId=" + getEmployeeId() +
            ", employeeEmployeeCode='" + getEmployeeEmployeeCode() + "'" +
            "}";
    }
}
