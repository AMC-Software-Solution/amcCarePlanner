package com.amc.careplanner.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A TerminalDevice.
 */
@Entity
@Table(name = "terminal_device")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TerminalDevice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "device_name")
    private String deviceName;

    @Column(name = "device_model")
    private String deviceModel;

    @Column(name = "registered_date")
    private ZonedDateTime registeredDate;

    @Column(name = "imei")
    private String imei;

    @Column(name = "sim_number")
    private String simNumber;

    @Column(name = "user_started_using_from")
    private ZonedDateTime userStartedUsingFrom;

    @Column(name = "device_on_location_from")
    private ZonedDateTime deviceOnLocationFrom;

    @Column(name = "operating_system")
    private String operatingSystem;

    @Column(name = "note")
    private String note;

    @Column(name = "owner_entity_id")
    private Long ownerEntityId;

    @Column(name = "owner_entity_name")
    private String ownerEntityName;

    @Column(name = "last_updated_date")
    private ZonedDateTime lastUpdatedDate;

    @NotNull
    @Column(name = "tenant_id", nullable = false)
    private Long tenantId;

    @ManyToOne
    @JsonIgnoreProperties(value = "terminalDevices", allowSetters = true)
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public TerminalDevice deviceName(String deviceName) {
        this.deviceName = deviceName;
        return this;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public TerminalDevice deviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
        return this;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public ZonedDateTime getRegisteredDate() {
        return registeredDate;
    }

    public TerminalDevice registeredDate(ZonedDateTime registeredDate) {
        this.registeredDate = registeredDate;
        return this;
    }

    public void setRegisteredDate(ZonedDateTime registeredDate) {
        this.registeredDate = registeredDate;
    }

    public String getImei() {
        return imei;
    }

    public TerminalDevice imei(String imei) {
        this.imei = imei;
        return this;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getSimNumber() {
        return simNumber;
    }

    public TerminalDevice simNumber(String simNumber) {
        this.simNumber = simNumber;
        return this;
    }

    public void setSimNumber(String simNumber) {
        this.simNumber = simNumber;
    }

    public ZonedDateTime getUserStartedUsingFrom() {
        return userStartedUsingFrom;
    }

    public TerminalDevice userStartedUsingFrom(ZonedDateTime userStartedUsingFrom) {
        this.userStartedUsingFrom = userStartedUsingFrom;
        return this;
    }

    public void setUserStartedUsingFrom(ZonedDateTime userStartedUsingFrom) {
        this.userStartedUsingFrom = userStartedUsingFrom;
    }

    public ZonedDateTime getDeviceOnLocationFrom() {
        return deviceOnLocationFrom;
    }

    public TerminalDevice deviceOnLocationFrom(ZonedDateTime deviceOnLocationFrom) {
        this.deviceOnLocationFrom = deviceOnLocationFrom;
        return this;
    }

    public void setDeviceOnLocationFrom(ZonedDateTime deviceOnLocationFrom) {
        this.deviceOnLocationFrom = deviceOnLocationFrom;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public TerminalDevice operatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
        return this;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getNote() {
        return note;
    }

    public TerminalDevice note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getOwnerEntityId() {
        return ownerEntityId;
    }

    public TerminalDevice ownerEntityId(Long ownerEntityId) {
        this.ownerEntityId = ownerEntityId;
        return this;
    }

    public void setOwnerEntityId(Long ownerEntityId) {
        this.ownerEntityId = ownerEntityId;
    }

    public String getOwnerEntityName() {
        return ownerEntityName;
    }

    public TerminalDevice ownerEntityName(String ownerEntityName) {
        this.ownerEntityName = ownerEntityName;
        return this;
    }

    public void setOwnerEntityName(String ownerEntityName) {
        this.ownerEntityName = ownerEntityName;
    }

    public ZonedDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public TerminalDevice lastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
        return this;
    }

    public void setLastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public TerminalDevice tenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public TerminalDevice employee(Employee employee) {
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
        if (!(o instanceof TerminalDevice)) {
            return false;
        }
        return id != null && id.equals(((TerminalDevice) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TerminalDevice{" +
            "id=" + getId() +
            ", deviceName='" + getDeviceName() + "'" +
            ", deviceModel='" + getDeviceModel() + "'" +
            ", registeredDate='" + getRegisteredDate() + "'" +
            ", imei='" + getImei() + "'" +
            ", simNumber='" + getSimNumber() + "'" +
            ", userStartedUsingFrom='" + getUserStartedUsingFrom() + "'" +
            ", deviceOnLocationFrom='" + getDeviceOnLocationFrom() + "'" +
            ", operatingSystem='" + getOperatingSystem() + "'" +
            ", note='" + getNote() + "'" +
            ", ownerEntityId=" + getOwnerEntityId() +
            ", ownerEntityName='" + getOwnerEntityName() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", tenantId=" + getTenantId() +
            "}";
    }
}
