package com.amc.careplanner.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

import com.amc.careplanner.domain.enumeration.TaskStatus;

/**
 * A Task.
 */
@Entity
@Table(name = "task")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "task_name", nullable = false)
    private String taskName;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "date_of_task", nullable = false)
    private LocalDate dateOfTask;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private String startTime;

    @Column(name = "end_time")
    private String endTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TaskStatus status;

    @Column(name = "date_created")
    private ZonedDateTime dateCreated;

    @Column(name = "last_updated_date")
    private ZonedDateTime lastUpdatedDate;

    @NotNull
    @Column(name = "tenant_id", nullable = false)
    private Long tenantId;

    @ManyToOne
    @JsonIgnoreProperties(value = "tasks", allowSetters = true)
    private ServiceUser serviceUser;

    @ManyToOne
    @JsonIgnoreProperties(value = "tasks", allowSetters = true)
    private Employee assignedTo;

    @ManyToOne
    @JsonIgnoreProperties(value = "tasks", allowSetters = true)
    private ServiceOrder serviceOrder;

    @ManyToOne
    @JsonIgnoreProperties(value = "tasks", allowSetters = true)
    private Employee createdBy;

    @ManyToOne
    @JsonIgnoreProperties(value = "tasks", allowSetters = true)
    private Employee allocatedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public Task taskName(String taskName) {
        this.taskName = taskName;
        return this;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public Task description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateOfTask() {
        return dateOfTask;
    }

    public Task dateOfTask(LocalDate dateOfTask) {
        this.dateOfTask = dateOfTask;
        return this;
    }

    public void setDateOfTask(LocalDate dateOfTask) {
        this.dateOfTask = dateOfTask;
    }

    public String getStartTime() {
        return startTime;
    }

    public Task startTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public Task endTime(String endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public Task status(TaskStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public Task dateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public ZonedDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public Task lastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
        return this;
    }

    public void setLastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public Task tenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public ServiceUser getServiceUser() {
        return serviceUser;
    }

    public Task serviceUser(ServiceUser serviceUser) {
        this.serviceUser = serviceUser;
        return this;
    }

    public void setServiceUser(ServiceUser serviceUser) {
        this.serviceUser = serviceUser;
    }

    public Employee getAssignedTo() {
        return assignedTo;
    }

    public Task assignedTo(Employee employee) {
        this.assignedTo = employee;
        return this;
    }

    public void setAssignedTo(Employee employee) {
        this.assignedTo = employee;
    }

    public ServiceOrder getServiceOrder() {
        return serviceOrder;
    }

    public Task serviceOrder(ServiceOrder serviceOrder) {
        this.serviceOrder = serviceOrder;
        return this;
    }

    public void setServiceOrder(ServiceOrder serviceOrder) {
        this.serviceOrder = serviceOrder;
    }

    public Employee getCreatedBy() {
        return createdBy;
    }

    public Task createdBy(Employee employee) {
        this.createdBy = employee;
        return this;
    }

    public void setCreatedBy(Employee employee) {
        this.createdBy = employee;
    }

    public Employee getAllocatedBy() {
        return allocatedBy;
    }

    public Task allocatedBy(Employee employee) {
        this.allocatedBy = employee;
        return this;
    }

    public void setAllocatedBy(Employee employee) {
        this.allocatedBy = employee;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Task)) {
            return false;
        }
        return id != null && id.equals(((Task) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Task{" +
            "id=" + getId() +
            ", taskName='" + getTaskName() + "'" +
            ", description='" + getDescription() + "'" +
            ", dateOfTask='" + getDateOfTask() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", status='" + getStatus() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", tenantId=" + getTenantId() +
            "}";
    }
}
