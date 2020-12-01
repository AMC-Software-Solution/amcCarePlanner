package com.amc.careplanner.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import com.amc.careplanner.domain.enumeration.TravelMode;
import com.amc.careplanner.domain.enumeration.TravelStatus;

/**
 * A DTO for the {@link com.amc.careplanner.domain.Travel} entity.
 */
public class TravelDTO implements Serializable {
    
    private Long id;

    @NotNull
    private TravelMode travelMode;

    private Long distanceToDestination;

    private Long timeToDestination;

    private Long actualDistanceRequired;

    private Long actualTimeRequired;

    private TravelStatus travelStatus;

    private ZonedDateTime lastUpdatedDate;

    @NotNull
    private Long clientId;


    private Long taskId;

    private String taskTaskName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TravelMode getTravelMode() {
        return travelMode;
    }

    public void setTravelMode(TravelMode travelMode) {
        this.travelMode = travelMode;
    }

    public Long getDistanceToDestination() {
        return distanceToDestination;
    }

    public void setDistanceToDestination(Long distanceToDestination) {
        this.distanceToDestination = distanceToDestination;
    }

    public Long getTimeToDestination() {
        return timeToDestination;
    }

    public void setTimeToDestination(Long timeToDestination) {
        this.timeToDestination = timeToDestination;
    }

    public Long getActualDistanceRequired() {
        return actualDistanceRequired;
    }

    public void setActualDistanceRequired(Long actualDistanceRequired) {
        this.actualDistanceRequired = actualDistanceRequired;
    }

    public Long getActualTimeRequired() {
        return actualTimeRequired;
    }

    public void setActualTimeRequired(Long actualTimeRequired) {
        this.actualTimeRequired = actualTimeRequired;
    }

    public TravelStatus getTravelStatus() {
        return travelStatus;
    }

    public void setTravelStatus(TravelStatus travelStatus) {
        this.travelStatus = travelStatus;
    }

    public ZonedDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTaskTaskName() {
        return taskTaskName;
    }

    public void setTaskTaskName(String taskTaskName) {
        this.taskTaskName = taskTaskName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TravelDTO)) {
            return false;
        }

        return id != null && id.equals(((TravelDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TravelDTO{" +
            "id=" + getId() +
            ", travelMode='" + getTravelMode() + "'" +
            ", distanceToDestination=" + getDistanceToDestination() +
            ", timeToDestination=" + getTimeToDestination() +
            ", actualDistanceRequired=" + getActualDistanceRequired() +
            ", actualTimeRequired=" + getActualTimeRequired() +
            ", travelStatus='" + getTravelStatus() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", clientId=" + getClientId() +
            ", taskId=" + getTaskId() +
            ", taskTaskName='" + getTaskTaskName() + "'" +
            "}";
    }
}
