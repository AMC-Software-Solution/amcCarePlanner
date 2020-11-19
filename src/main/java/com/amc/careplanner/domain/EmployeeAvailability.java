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

    @Column(name = "is_available_for_work")
    private Boolean isAvailableForWork;

    @Column(name = "minimum_hours_per_week")
    private Integer minimumHoursPerWeek;

    @Column(name = "maximum_hours_per_week")
    private Integer maximumHoursPerWeek;

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

    public Boolean isIsAvailableForWork() {
        return isAvailableForWork;
    }

    public EmployeeAvailability isAvailableForWork(Boolean isAvailableForWork) {
        this.isAvailableForWork = isAvailableForWork;
        return this;
    }

    public void setIsAvailableForWork(Boolean isAvailableForWork) {
        this.isAvailableForWork = isAvailableForWork;
    }

    public Integer getMinimumHoursPerWeek() {
        return minimumHoursPerWeek;
    }

    public EmployeeAvailability minimumHoursPerWeek(Integer minimumHoursPerWeek) {
        this.minimumHoursPerWeek = minimumHoursPerWeek;
        return this;
    }

    public void setMinimumHoursPerWeek(Integer minimumHoursPerWeek) {
        this.minimumHoursPerWeek = minimumHoursPerWeek;
    }

    public Integer getMaximumHoursPerWeek() {
        return maximumHoursPerWeek;
    }

    public EmployeeAvailability maximumHoursPerWeek(Integer maximumHoursPerWeek) {
        this.maximumHoursPerWeek = maximumHoursPerWeek;
        return this;
    }

    public void setMaximumHoursPerWeek(Integer maximumHoursPerWeek) {
        this.maximumHoursPerWeek = maximumHoursPerWeek;
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
            ", isAvailableForWork='" + isIsAvailableForWork() + "'" +
            ", minimumHoursPerWeek=" + getMinimumHoursPerWeek() +
            ", maximumHoursPerWeek=" + getMaximumHoursPerWeek() +
            ", leastPreferredShift='" + getLeastPreferredShift() + "'" +
            "}";
    }
}
