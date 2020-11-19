package com.amc.careplanner.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A SystemEventsHistory.
 */
@Entity
@Table(name = "system_events_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SystemEventsHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "event_name", nullable = false)
    private String eventName;

    @Column(name = "event_date")
    private ZonedDateTime eventDate;

    @Column(name = "event_api")
    private String eventApi;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "event_note")
    private String eventNote;

    @Column(name = "event_entity_name")
    private String eventEntityName;

    @Column(name = "event_entity_id")
    private Long eventEntityId;

    @Column(name = "is_suspecious")
    private Boolean isSuspecious;

    @Column(name = "tenant_id")
    private Long tenantId;

    @ManyToOne
    @JsonIgnoreProperties(value = "systemEventsHistories", allowSetters = true)
    private User triggedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public SystemEventsHistory eventName(String eventName) {
        this.eventName = eventName;
        return this;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public ZonedDateTime getEventDate() {
        return eventDate;
    }

    public SystemEventsHistory eventDate(ZonedDateTime eventDate) {
        this.eventDate = eventDate;
        return this;
    }

    public void setEventDate(ZonedDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventApi() {
        return eventApi;
    }

    public SystemEventsHistory eventApi(String eventApi) {
        this.eventApi = eventApi;
        return this;
    }

    public void setEventApi(String eventApi) {
        this.eventApi = eventApi;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public SystemEventsHistory ipAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getEventNote() {
        return eventNote;
    }

    public SystemEventsHistory eventNote(String eventNote) {
        this.eventNote = eventNote;
        return this;
    }

    public void setEventNote(String eventNote) {
        this.eventNote = eventNote;
    }

    public String getEventEntityName() {
        return eventEntityName;
    }

    public SystemEventsHistory eventEntityName(String eventEntityName) {
        this.eventEntityName = eventEntityName;
        return this;
    }

    public void setEventEntityName(String eventEntityName) {
        this.eventEntityName = eventEntityName;
    }

    public Long getEventEntityId() {
        return eventEntityId;
    }

    public SystemEventsHistory eventEntityId(Long eventEntityId) {
        this.eventEntityId = eventEntityId;
        return this;
    }

    public void setEventEntityId(Long eventEntityId) {
        this.eventEntityId = eventEntityId;
    }

    public Boolean isIsSuspecious() {
        return isSuspecious;
    }

    public SystemEventsHistory isSuspecious(Boolean isSuspecious) {
        this.isSuspecious = isSuspecious;
        return this;
    }

    public void setIsSuspecious(Boolean isSuspecious) {
        this.isSuspecious = isSuspecious;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public SystemEventsHistory tenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public User getTriggedBy() {
        return triggedBy;
    }

    public SystemEventsHistory triggedBy(User user) {
        this.triggedBy = user;
        return this;
    }

    public void setTriggedBy(User user) {
        this.triggedBy = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SystemEventsHistory)) {
            return false;
        }
        return id != null && id.equals(((SystemEventsHistory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SystemEventsHistory{" +
            "id=" + getId() +
            ", eventName='" + getEventName() + "'" +
            ", eventDate='" + getEventDate() + "'" +
            ", eventApi='" + getEventApi() + "'" +
            ", ipAddress='" + getIpAddress() + "'" +
            ", eventNote='" + getEventNote() + "'" +
            ", eventEntityName='" + getEventEntityName() + "'" +
            ", eventEntityId=" + getEventEntityId() +
            ", isSuspecious='" + isIsSuspecious() + "'" +
            ", tenantId=" + getTenantId() +
            "}";
    }
}
