package com.amc.careplanner.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

import com.amc.careplanner.domain.enumeration.TravelMode;

/**
 * A Travel.
 */
@Entity
@Table(name = "travel")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Travel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "travel_mode", nullable = false)
    private TravelMode travelMode;

    @Column(name = "distance_to_destination")
    private Long distanceToDestination;

    @Column(name = "time_to_destination")
    private Long timeToDestination;

    @Column(name = "actual_distance_required")
    private Long actualDistanceRequired;

    @Column(name = "actual_time_required")
    private Long actualTimeRequired;

    @Column(name = "last_updated_date")
    private ZonedDateTime lastUpdatedDate;

    @NotNull
    @Column(name = "tenant_id", nullable = false)
    private Long tenantId;

    @ManyToOne
    @JsonIgnoreProperties(value = "travels", allowSetters = true)
    private Task task;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TravelMode getTravelMode() {
        return travelMode;
    }

    public Travel travelMode(TravelMode travelMode) {
        this.travelMode = travelMode;
        return this;
    }

    public void setTravelMode(TravelMode travelMode) {
        this.travelMode = travelMode;
    }

    public Long getDistanceToDestination() {
        return distanceToDestination;
    }

    public Travel distanceToDestination(Long distanceToDestination) {
        this.distanceToDestination = distanceToDestination;
        return this;
    }

    public void setDistanceToDestination(Long distanceToDestination) {
        this.distanceToDestination = distanceToDestination;
    }

    public Long getTimeToDestination() {
        return timeToDestination;
    }

    public Travel timeToDestination(Long timeToDestination) {
        this.timeToDestination = timeToDestination;
        return this;
    }

    public void setTimeToDestination(Long timeToDestination) {
        this.timeToDestination = timeToDestination;
    }

    public Long getActualDistanceRequired() {
        return actualDistanceRequired;
    }

    public Travel actualDistanceRequired(Long actualDistanceRequired) {
        this.actualDistanceRequired = actualDistanceRequired;
        return this;
    }

    public void setActualDistanceRequired(Long actualDistanceRequired) {
        this.actualDistanceRequired = actualDistanceRequired;
    }

    public Long getActualTimeRequired() {
        return actualTimeRequired;
    }

    public Travel actualTimeRequired(Long actualTimeRequired) {
        this.actualTimeRequired = actualTimeRequired;
        return this;
    }

    public void setActualTimeRequired(Long actualTimeRequired) {
        this.actualTimeRequired = actualTimeRequired;
    }

    public ZonedDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public Travel lastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
        return this;
    }

    public void setLastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public Travel tenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Task getTask() {
        return task;
    }

    public Travel task(Task task) {
        this.task = task;
        return this;
    }

    public void setTask(Task task) {
        this.task = task;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Travel)) {
            return false;
        }
        return id != null && id.equals(((Travel) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Travel{" +
            "id=" + getId() +
            ", travelMode='" + getTravelMode() + "'" +
            ", distanceToDestination=" + getDistanceToDestination() +
            ", timeToDestination=" + getTimeToDestination() +
            ", actualDistanceRequired=" + getActualDistanceRequired() +
            ", actualTimeRequired=" + getActualTimeRequired() +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", tenantId=" + getTenantId() +
            "}";
    }
}
