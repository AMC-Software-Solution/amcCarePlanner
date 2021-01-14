package com.amc.careplanner.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

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

    @Column(name = "available_for_work")
    private Boolean availableForWork;

    @Column(name = "available_monday")
    private Boolean availableMonday;

    @Column(name = "available_tuesday")
    private Boolean availableTuesday;

    @Column(name = "available_wednesday")
    private Boolean availableWednesday;

    @Column(name = "available_thursday")
    private Boolean availableThursday;

    @Column(name = "available_friday")
    private Boolean availableFriday;

    @Column(name = "available_saturday")
    private Boolean availableSaturday;

    @Column(name = "available_sunday")
    private Boolean availableSunday;

    @Enumerated(EnumType.STRING)
    @Column(name = "preferred_shift")
    private Shift preferredShift;

    @Column(name = "has_extra_data")
    private Boolean hasExtraData;

    @Column(name = "last_updated_date")
    private ZonedDateTime lastUpdatedDate;

    @NotNull
    @Column(name = "client_id", nullable = false)
    private Long clientId;

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

    public Boolean isAvailableForWork() {
        return availableForWork;
    }

    public EmployeeAvailability availableForWork(Boolean availableForWork) {
        this.availableForWork = availableForWork;
        return this;
    }

    public void setAvailableForWork(Boolean availableForWork) {
        this.availableForWork = availableForWork;
    }

    public Boolean isAvailableMonday() {
        return availableMonday;
    }

    public EmployeeAvailability availableMonday(Boolean availableMonday) {
        this.availableMonday = availableMonday;
        return this;
    }

    public void setAvailableMonday(Boolean availableMonday) {
        this.availableMonday = availableMonday;
    }

    public Boolean isAvailableTuesday() {
        return availableTuesday;
    }

    public EmployeeAvailability availableTuesday(Boolean availableTuesday) {
        this.availableTuesday = availableTuesday;
        return this;
    }

    public void setAvailableTuesday(Boolean availableTuesday) {
        this.availableTuesday = availableTuesday;
    }

    public Boolean isAvailableWednesday() {
        return availableWednesday;
    }

    public EmployeeAvailability availableWednesday(Boolean availableWednesday) {
        this.availableWednesday = availableWednesday;
        return this;
    }

    public void setAvailableWednesday(Boolean availableWednesday) {
        this.availableWednesday = availableWednesday;
    }

    public Boolean isAvailableThursday() {
        return availableThursday;
    }

    public EmployeeAvailability availableThursday(Boolean availableThursday) {
        this.availableThursday = availableThursday;
        return this;
    }

    public void setAvailableThursday(Boolean availableThursday) {
        this.availableThursday = availableThursday;
    }

    public Boolean isAvailableFriday() {
        return availableFriday;
    }

    public EmployeeAvailability availableFriday(Boolean availableFriday) {
        this.availableFriday = availableFriday;
        return this;
    }

    public void setAvailableFriday(Boolean availableFriday) {
        this.availableFriday = availableFriday;
    }

    public Boolean isAvailableSaturday() {
        return availableSaturday;
    }

    public EmployeeAvailability availableSaturday(Boolean availableSaturday) {
        this.availableSaturday = availableSaturday;
        return this;
    }

    public void setAvailableSaturday(Boolean availableSaturday) {
        this.availableSaturday = availableSaturday;
    }

    public Boolean isAvailableSunday() {
        return availableSunday;
    }

    public EmployeeAvailability availableSunday(Boolean availableSunday) {
        this.availableSunday = availableSunday;
        return this;
    }

    public void setAvailableSunday(Boolean availableSunday) {
        this.availableSunday = availableSunday;
    }

    public Shift getPreferredShift() {
        return preferredShift;
    }

    public EmployeeAvailability preferredShift(Shift preferredShift) {
        this.preferredShift = preferredShift;
        return this;
    }

    public void setPreferredShift(Shift preferredShift) {
        this.preferredShift = preferredShift;
    }

    public Boolean isHasExtraData() {
        return hasExtraData;
    }

    public EmployeeAvailability hasExtraData(Boolean hasExtraData) {
        this.hasExtraData = hasExtraData;
        return this;
    }

    public void setHasExtraData(Boolean hasExtraData) {
        this.hasExtraData = hasExtraData;
    }

    public ZonedDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public EmployeeAvailability lastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
        return this;
    }

    public void setLastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Long getClientId() {
        return clientId;
    }

    public EmployeeAvailability clientId(Long clientId) {
        this.clientId = clientId;
        return this;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
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
            ", availableForWork='" + isAvailableForWork() + "'" +
            ", availableMonday='" + isAvailableMonday() + "'" +
            ", availableTuesday='" + isAvailableTuesday() + "'" +
            ", availableWednesday='" + isAvailableWednesday() + "'" +
            ", availableThursday='" + isAvailableThursday() + "'" +
            ", availableFriday='" + isAvailableFriday() + "'" +
            ", availableSaturday='" + isAvailableSaturday() + "'" +
            ", availableSunday='" + isAvailableSunday() + "'" +
            ", preferredShift='" + getPreferredShift() + "'" +
            ", hasExtraData='" + isHasExtraData() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", clientId=" + getClientId() +
            "}";
    }
}
