package com.amc.careplanner.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

/**
 * A Timesheet.
 */
@Entity
@Table(name = "timesheet")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Timesheet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "timesheet_date", nullable = false)
    private LocalDate timesheetDate;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private String startTime;

    @NotNull
    @Column(name = "end_time", nullable = false)
    private String endTime;

    @NotNull
    @Column(name = "hours_worked", nullable = false)
    private Integer hoursWorked;

    @Column(name = "break_start_time")
    private String breakStartTime;

    @Column(name = "break_end_time")
    private String breakEndTime;

    @Column(name = "note")
    private String note;

    @Column(name = "last_updated_date")
    private ZonedDateTime lastUpdatedDate;

    @NotNull
    @Column(name = "tenant_id", nullable = false)
    private Long tenantId;

    @OneToOne
    @JoinColumn(unique = true)
    private ServiceOrder serviceOrder;

    @ManyToOne
    @JsonIgnoreProperties(value = "timesheets", allowSetters = true)
    private ServiceUser serviceUser;

    @ManyToOne
    @JsonIgnoreProperties(value = "timesheets", allowSetters = true)
    private Employee careProvider;

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

    public Timesheet description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getTimesheetDate() {
        return timesheetDate;
    }

    public Timesheet timesheetDate(LocalDate timesheetDate) {
        this.timesheetDate = timesheetDate;
        return this;
    }

    public void setTimesheetDate(LocalDate timesheetDate) {
        this.timesheetDate = timesheetDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public Timesheet startTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public Timesheet endTime(String endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getHoursWorked() {
        return hoursWorked;
    }

    public Timesheet hoursWorked(Integer hoursWorked) {
        this.hoursWorked = hoursWorked;
        return this;
    }

    public void setHoursWorked(Integer hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public String getBreakStartTime() {
        return breakStartTime;
    }

    public Timesheet breakStartTime(String breakStartTime) {
        this.breakStartTime = breakStartTime;
        return this;
    }

    public void setBreakStartTime(String breakStartTime) {
        this.breakStartTime = breakStartTime;
    }

    public String getBreakEndTime() {
        return breakEndTime;
    }

    public Timesheet breakEndTime(String breakEndTime) {
        this.breakEndTime = breakEndTime;
        return this;
    }

    public void setBreakEndTime(String breakEndTime) {
        this.breakEndTime = breakEndTime;
    }

    public String getNote() {
        return note;
    }

    public Timesheet note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public ZonedDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public Timesheet lastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
        return this;
    }

    public void setLastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public Timesheet tenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public ServiceOrder getServiceOrder() {
        return serviceOrder;
    }

    public Timesheet serviceOrder(ServiceOrder serviceOrder) {
        this.serviceOrder = serviceOrder;
        return this;
    }

    public void setServiceOrder(ServiceOrder serviceOrder) {
        this.serviceOrder = serviceOrder;
    }

    public ServiceUser getServiceUser() {
        return serviceUser;
    }

    public Timesheet serviceUser(ServiceUser serviceUser) {
        this.serviceUser = serviceUser;
        return this;
    }

    public void setServiceUser(ServiceUser serviceUser) {
        this.serviceUser = serviceUser;
    }

    public Employee getCareProvider() {
        return careProvider;
    }

    public Timesheet careProvider(Employee employee) {
        this.careProvider = employee;
        return this;
    }

    public void setCareProvider(Employee employee) {
        this.careProvider = employee;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Timesheet)) {
            return false;
        }
        return id != null && id.equals(((Timesheet) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Timesheet{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", timesheetDate='" + getTimesheetDate() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", hoursWorked=" + getHoursWorked() +
            ", breakStartTime='" + getBreakStartTime() + "'" +
            ", breakEndTime='" + getBreakEndTime() + "'" +
            ", note='" + getNote() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", tenantId=" + getTenantId() +
            "}";
    }
}
