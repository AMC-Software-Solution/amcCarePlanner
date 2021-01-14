package com.amc.careplanner.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

import com.amc.careplanner.domain.enumeration.EmployeeHolidayType;

import com.amc.careplanner.domain.enumeration.HolidayStatus;

/**
 * A EmployeeHoliday.
 */
@Entity
@Table(name = "employee_holiday")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmployeeHoliday implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "start_date")
    private ZonedDateTime startDate;

    @Column(name = "end_date")
    private ZonedDateTime endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "employee_holiday_type")
    private EmployeeHolidayType employeeHolidayType;

    @Column(name = "approved_date")
    private ZonedDateTime approvedDate;

    @Column(name = "requested_date")
    private ZonedDateTime requestedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "holiday_status")
    private HolidayStatus holidayStatus;

    @Column(name = "note")
    private String note;

    @Column(name = "rejection_reason")
    private String rejectionReason;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "last_updated_date")
    private ZonedDateTime lastUpdatedDate;

    @NotNull
    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @Column(name = "has_extra_data")
    private Boolean hasExtraData;

    @ManyToOne
    @JsonIgnoreProperties(value = "employeeHolidays", allowSetters = true)
    private Employee employee;

    @ManyToOne
    @JsonIgnoreProperties(value = "employeeHolidays", allowSetters = true)
    private Employee approvedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public EmployeeHoliday description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public EmployeeHoliday startDate(ZonedDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public EmployeeHoliday endDate(ZonedDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public EmployeeHolidayType getEmployeeHolidayType() {
        return employeeHolidayType;
    }

    public EmployeeHoliday employeeHolidayType(EmployeeHolidayType employeeHolidayType) {
        this.employeeHolidayType = employeeHolidayType;
        return this;
    }

    public void setEmployeeHolidayType(EmployeeHolidayType employeeHolidayType) {
        this.employeeHolidayType = employeeHolidayType;
    }

    public ZonedDateTime getApprovedDate() {
        return approvedDate;
    }

    public EmployeeHoliday approvedDate(ZonedDateTime approvedDate) {
        this.approvedDate = approvedDate;
        return this;
    }

    public void setApprovedDate(ZonedDateTime approvedDate) {
        this.approvedDate = approvedDate;
    }

    public ZonedDateTime getRequestedDate() {
        return requestedDate;
    }

    public EmployeeHoliday requestedDate(ZonedDateTime requestedDate) {
        this.requestedDate = requestedDate;
        return this;
    }

    public void setRequestedDate(ZonedDateTime requestedDate) {
        this.requestedDate = requestedDate;
    }

    public HolidayStatus getHolidayStatus() {
        return holidayStatus;
    }

    public EmployeeHoliday holidayStatus(HolidayStatus holidayStatus) {
        this.holidayStatus = holidayStatus;
        return this;
    }

    public void setHolidayStatus(HolidayStatus holidayStatus) {
        this.holidayStatus = holidayStatus;
    }

    public String getNote() {
        return note;
    }

    public EmployeeHoliday note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public EmployeeHoliday rejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
        return this;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public EmployeeHoliday createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public EmployeeHoliday lastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
        return this;
    }

    public void setLastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Long getClientId() {
        return clientId;
    }

    public EmployeeHoliday clientId(Long clientId) {
        this.clientId = clientId;
        return this;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Boolean isHasExtraData() {
        return hasExtraData;
    }

    public EmployeeHoliday hasExtraData(Boolean hasExtraData) {
        this.hasExtraData = hasExtraData;
        return this;
    }

    public void setHasExtraData(Boolean hasExtraData) {
        this.hasExtraData = hasExtraData;
    }

    public Employee getEmployee() {
        return employee;
    }

    public EmployeeHoliday employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Employee getApprovedBy() {
        return approvedBy;
    }

    public EmployeeHoliday approvedBy(Employee employee) {
        this.approvedBy = employee;
        return this;
    }

    public void setApprovedBy(Employee employee) {
        this.approvedBy = employee;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeHoliday)) {
            return false;
        }
        return id != null && id.equals(((EmployeeHoliday) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeHoliday{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", employeeHolidayType='" + getEmployeeHolidayType() + "'" +
            ", approvedDate='" + getApprovedDate() + "'" +
            ", requestedDate='" + getRequestedDate() + "'" +
            ", holidayStatus='" + getHolidayStatus() + "'" +
            ", note='" + getNote() + "'" +
            ", rejectionReason='" + getRejectionReason() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", clientId=" + getClientId() +
            ", hasExtraData='" + isHasExtraData() + "'" +
            "}";
    }
}
