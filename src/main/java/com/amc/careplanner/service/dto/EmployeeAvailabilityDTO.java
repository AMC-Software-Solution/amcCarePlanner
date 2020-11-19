package com.amc.careplanner.service.dto;

import java.io.Serializable;
import com.amc.careplanner.domain.enumeration.Shift;

/**
 * A DTO for the {@link com.amc.careplanner.domain.EmployeeAvailability} entity.
 */
public class EmployeeAvailabilityDTO implements Serializable {
    
    private Long id;

    private Boolean isAvailableForWork;

    private Integer minimumHoursPerWeek;

    private Integer maximumHoursPerWeek;

    private Shift leastPreferredShift;


    private Long employeeId;

    private String employeeEmployeeCode;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIsAvailableForWork() {
        return isAvailableForWork;
    }

    public void setIsAvailableForWork(Boolean isAvailableForWork) {
        this.isAvailableForWork = isAvailableForWork;
    }

    public Integer getMinimumHoursPerWeek() {
        return minimumHoursPerWeek;
    }

    public void setMinimumHoursPerWeek(Integer minimumHoursPerWeek) {
        this.minimumHoursPerWeek = minimumHoursPerWeek;
    }

    public Integer getMaximumHoursPerWeek() {
        return maximumHoursPerWeek;
    }

    public void setMaximumHoursPerWeek(Integer maximumHoursPerWeek) {
        this.maximumHoursPerWeek = maximumHoursPerWeek;
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
            ", isAvailableForWork='" + isIsAvailableForWork() + "'" +
            ", minimumHoursPerWeek=" + getMinimumHoursPerWeek() +
            ", maximumHoursPerWeek=" + getMaximumHoursPerWeek() +
            ", leastPreferredShift='" + getLeastPreferredShift() + "'" +
            ", employeeId=" + getEmployeeId() +
            ", employeeEmployeeCode='" + getEmployeeEmployeeCode() + "'" +
            "}";
    }
}
