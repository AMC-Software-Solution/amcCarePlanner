package com.amc.careplanner.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.amc.careplanner.domain.SystemEventsHistory} entity.
 */
public class SystemEventsHistoryDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String eventName;

    private ZonedDateTime eventDate;

    private String eventApi;

    private String ipAddress;

    private String eventNote;

    private String eventEntityName;

    private Long eventEntityId;

    private Boolean isSuspecious;

    private Long tenantId;


    private Long triggedById;

    private String triggedByLogin;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public ZonedDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(ZonedDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventApi() {
        return eventApi;
    }

    public void setEventApi(String eventApi) {
        this.eventApi = eventApi;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getEventNote() {
        return eventNote;
    }

    public void setEventNote(String eventNote) {
        this.eventNote = eventNote;
    }

    public String getEventEntityName() {
        return eventEntityName;
    }

    public void setEventEntityName(String eventEntityName) {
        this.eventEntityName = eventEntityName;
    }

    public Long getEventEntityId() {
        return eventEntityId;
    }

    public void setEventEntityId(Long eventEntityId) {
        this.eventEntityId = eventEntityId;
    }

    public Boolean isIsSuspecious() {
        return isSuspecious;
    }

    public void setIsSuspecious(Boolean isSuspecious) {
        this.isSuspecious = isSuspecious;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getTriggedById() {
        return triggedById;
    }

    public void setTriggedById(Long userId) {
        this.triggedById = userId;
    }

    public String getTriggedByLogin() {
        return triggedByLogin;
    }

    public void setTriggedByLogin(String userLogin) {
        this.triggedByLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SystemEventsHistoryDTO)) {
            return false;
        }

        return id != null && id.equals(((SystemEventsHistoryDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SystemEventsHistoryDTO{" +
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
            ", triggedById=" + getTriggedById() +
            ", triggedByLogin='" + getTriggedByLogin() + "'" +
            "}";
    }
}
