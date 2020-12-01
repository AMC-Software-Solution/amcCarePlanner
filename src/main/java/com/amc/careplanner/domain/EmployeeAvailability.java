package com.amc.careplanner.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import com.amc.careplanner.domain.enumeration.Shift;

/**
 * A EmployeeAvailability.
 */
@Entity
@Table(name = "employee_availability")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmployeeAvailability implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_available_for_work_week_days")
    private Boolean isAvailableForWorkWeekDays;

    @Column(name = "minimum_hours_per_week_week_days")
    private Integer minimumHoursPerWeekWeekDays;

    @Column(name = "maximum_hours_per_week_week_days")
    private Integer maximumHoursPerWeekWeekDays;

    @Column(name = "is_available_for_work_week_ends")
    private Boolean isAvailableForWorkWeekEnds;

    @Column(name = "minimum_hours_per_week_week_ends")
    private Integer minimumHoursPerWeekWeekEnds;

    @Column(name = "maximum_hours_per_week_week_ends")
    private Integer maximumHoursPerWeekWeekEnds;

    @Enumerated(EnumType.STRING)
    @Column(name = "least_preferred_shift")
    private Shift leastPreferredShift;

    @ManyToOne
    @JsonIgnoreProperties(value = "employeeAvailabilities", allowSetters = true)
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIsAvailableForWorkWeekDays() {
        return isAvailableForWorkWeekDays;
    }

    public EmployeeAvailability isAvailableForWorkWeekDays(Boolean isAvailableForWorkWeekDays) {
        this.isAvailableForWorkWeekDays = isAvailableForWorkWeekDays;
        return this;
    }

    public void setIsAvailableForWorkWeekDays(Boolean isAvailableForWorkWeekDays) {
        this.isAvailableForWorkWeekDays = isAvailableForWorkWeekDays;
    }

    public Integer getMinimumHoursPerWeekWeekDays() {
        return minimumHoursPerWeekWeekDays;
    }

    public EmployeeAvailability minimumHoursPerWeekWeekDays(Integer minimumHoursPerWeekWeekDays) {
        this.minimumHoursPerWeekWeekDays = minimumHoursPerWeekWeekDays;
        return this;
    }

    public void setMinimumHoursPerWeekWeekDays(Integer minimumHoursPerWeekWeekDays) {
        this.minimumHoursPerWeekWeekDays = minimumHoursPerWeekWeekDays;
    }

    public Integer getMaximumHoursPerWeekWeekDays() {
        return maximumHoursPerWeekWeekDays;
    }

    public EmployeeAvailability maximumHoursPerWeekWeekDays(Integer maximumHoursPerWeekWeekDays) {
        this.maximumHoursPerWeekWeekDays = maximumHoursPerWeekWeekDays;
        return this;
    }

    public void setMaximumHoursPerWeekWeekDays(Integer maximumHoursPerWeekWeekDays) {
        this.maximumHoursPerWeekWeekDays = maximumHoursPerWeekWeekDays;
    }

    public Boolean isIsAvailableForWorkWeekEnds() {
        return isAvailableForWorkWeekEnds;
    }

    public EmployeeAvailability isAvailableForWorkWeekEnds(Boolean isAvailableForWorkWeekEnds) {
        this.isAvailableForWorkWeekEnds = isAvailableForWorkWeekEnds;
        return this;
    }

    public void setIsAvailableForWorkWeekEnds(Boolean isAvailableForWorkWeekEnds) {
        this.isAvailableForWorkWeekEnds = isAvailableForWorkWeekEnds;
    }

    public Integer getMinimumHoursPerWeekWeekEnds() {
        return minimumHoursPerWeekWeekEnds;
    }

    public EmployeeAvailability minimumHoursPerWeekWeekEnds(Integer minimumHoursPerWeekWeekEnds) {
        this.minimumHoursPerWeekWeekEnds = minimumHoursPerWeekWeekEnds;
        return this;
    }

    public void setMinimumHoursPerWeekWeekEnds(Integer minimumHoursPerWeekWeekEnds) {
        this.minimumHoursPerWeekWeekEnds = minimumHoursPerWeekWeekEnds;
    }

    public Integer getMaximumHoursPerWeekWeekEnds() {
        return maximumHoursPerWeekWeekEnds;
    }

    public EmployeeAvailability maximumHoursPerWeekWeekEnds(Integer maximumHoursPerWeekWeekEnds) {
        this.maximumHoursPerWeekWeekEnds = maximumHoursPerWeekWeekEnds;
        return this;
    }

    public void setMaximumHoursPerWeekWeekEnds(Integer maximumHoursPerWeekWeekEnds) {
        this.maximumHoursPerWeekWeekEnds = maximumHoursPerWeekWeekEnds;
    }

    public Shift getLeastPreferredShift() {
        return leastPreferredShift;
    }

    public EmployeeAvailability leastPreferredShift(Shift leastPreferredShift) {
        this.leastPreferredShift = leastPreferredShift;
        return this;
    }

    public void setLeastPreferredShift(Shift leastPreferredShift) {
        this.leastPreferredShift = leastPreferredShift;
    }

    public Employee getEmployee() {
        return employee;
    }

    public EmployeeAvailability employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeAvailability)) {
            return false;
        }
        return id != null && id.equals(((EmployeeAvailability) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeAvailability{" +
            "id=" + getId() +
            ", isAvailableForWorkWeekDays='" + isIsAvailableForWorkWeekDays() + "'" +
            ", minimumHoursPerWeekWeekDays=" + getMinimumHoursPerWeekWeekDays() +
            ", maximumHoursPerWeekWeekDays=" + getMaximumHoursPerWeekWeekDays() +
            ", isAvailableForWorkWeekEnds='" + isIsAvailableForWorkWeekEnds() + "'" +
            ", minimumHoursPerWeekWeekEnds=" + getMinimumHoursPerWeekWeekEnds() +
            ", maximumHoursPerWeekWeekEnds=" + getMaximumHoursPerWeekWeekEnds() +
            ", leastPreferredShift='" + getLeastPreferredShift() + "'" +
            "}";
    }
}
