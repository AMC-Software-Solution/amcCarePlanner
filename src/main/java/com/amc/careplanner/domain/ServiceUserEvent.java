package com.amc.careplanner.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

import com.amc.careplanner.domain.enumeration.ServiceUserEventStatus;

import com.amc.careplanner.domain.enumeration.ServiceUserEventType;

import com.amc.careplanner.domain.enumeration.ServicePriority;

/**
 * A ServiceUserEvent.
 */
@Entity
@Table(name = "service_user_event")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ServiceUserEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "event_title", nullable = false)
    private String eventTitle;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_user_event_status")
    private ServiceUserEventStatus serviceUserEventStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_user_event_type")
    private ServiceUserEventType serviceUserEventType;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private ServicePriority priority;

    @Column(name = "note")
    private String note;

    @NotNull
    @Column(name = "date_of_event", nullable = false)
    private ZonedDateTime dateOfEvent;

    @Column(name = "last_updated_date")
    private ZonedDateTime lastUpdatedDate;

    @NotNull
    @Column(name = "tenant_id", nullable = false)
    private Long tenantId;

    @ManyToOne
    @JsonIgnoreProperties(value = "serviceUserEvents", allowSetters = true)
    private Employee reportedBy;

    @ManyToOne
    @JsonIgnoreProperties(value = "serviceUserEvents", allowSetters = true)
    private Employee assignedTo;

    @ManyToOne
    @JsonIgnoreProperties(value = "serviceUserEvents", allowSetters = true)
    private ServiceUser serviceUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public ServiceUserEvent eventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
        return this;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getDescription() {
        return description;
    }

    public ServiceUserEvent description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ServiceUserEventStatus getServiceUserEventStatus() {
        return serviceUserEventStatus;
    }

    public ServiceUserEvent serviceUserEventStatus(ServiceUserEventStatus serviceUserEventStatus) {
        this.serviceUserEventStatus = serviceUserEventStatus;
        return this;
    }

    public void setServiceUserEventStatus(ServiceUserEventStatus serviceUserEventStatus) {
        this.serviceUserEventStatus = serviceUserEventStatus;
    }

    public ServiceUserEventType getServiceUserEventType() {
        return serviceUserEventType;
    }

    public ServiceUserEvent serviceUserEventType(ServiceUserEventType serviceUserEventType) {
        this.serviceUserEventType = serviceUserEventType;
        return this;
    }

    public void setServiceUserEventType(ServiceUserEventType serviceUserEventType) {
        this.serviceUserEventType = serviceUserEventType;
    }

    public ServicePriority getPriority() {
        return priority;
    }

    public ServiceUserEvent priority(ServicePriority priority) {
        this.priority = priority;
        return this;
    }

    public void setPriority(ServicePriority priority) {
        this.priority = priority;
    }

    public String getNote() {
        return note;
    }

    public ServiceUserEvent note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public ZonedDateTime getDateOfEvent() {
        return dateOfEvent;
    }

    public ServiceUserEvent dateOfEvent(ZonedDateTime dateOfEvent) {
        this.dateOfEvent = dateOfEvent;
        return this;
    }

    public void setDateOfEvent(ZonedDateTime dateOfEvent) {
        this.dateOfEvent = dateOfEvent;
    }

    public ZonedDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public ServiceUserEvent lastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
        return this;
    }

    public void setLastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public ServiceUserEvent tenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Employee getReportedBy() {
        return reportedBy;
    }

    public ServiceUserEvent reportedBy(Employee employee) {
        this.reportedBy = employee;
        return this;
    }

    public void setReportedBy(Employee employee) {
        this.reportedBy = employee;
    }

    public Employee getAssignedTo() {
        return assignedTo;
    }

    public ServiceUserEvent assignedTo(Employee employee) {
        this.assignedTo = employee;
        return this;
    }

    public void setAssignedTo(Employee employee) {
        this.assignedTo = employee;
    }

    public ServiceUser getServiceUser() {
        return serviceUser;
    }

    public ServiceUserEvent serviceUser(ServiceUser serviceUser) {
        this.serviceUser = serviceUser;
        return this;
    }

    public void setServiceUser(ServiceUser serviceUser) {
        this.serviceUser = serviceUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceUserEvent)) {
            return false;
        }
        return id != null && id.equals(((ServiceUserEvent) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceUserEvent{" +
            "id=" + getId() +
            ", eventTitle='" + getEventTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", serviceUserEventStatus='" + getServiceUserEventStatus() + "'" +
            ", serviceUserEventType='" + getServiceUserEventType() + "'" +
            ", priority='" + getPriority() + "'" +
            ", note='" + getNote() + "'" +
            ", dateOfEvent='" + getDateOfEvent() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", tenantId=" + getTenantId() +
            "}";
    }
}
