package com.amc.careplanner.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A CarerServiceUserRelation.
 */
@Entity
@Table(name = "carer_service_user_relation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CarerServiceUserRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reason")
    private String reason;

    @Column(name = "count")
    private Long count;

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
    @JsonIgnoreProperties(value = "carerServiceUserRelations", allowSetters = true)
    private RelationshipType relationType;

    @ManyToOne
    @JsonIgnoreProperties(value = "carerServiceUserRelations", allowSetters = true)
    private Employee employee;

    @ManyToOne
    @JsonIgnoreProperties(value = "carerServiceUserRelations", allowSetters = true)
    private ServiceUser serviceUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public CarerServiceUserRelation reason(String reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getCount() {
        return count;
    }

    public CarerServiceUserRelation count(Long count) {
        this.count = count;
        return this;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public CarerServiceUserRelation createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public CarerServiceUserRelation lastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
        return this;
    }

    public void setLastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Long getClientId() {
        return clientId;
    }

    public CarerServiceUserRelation clientId(Long clientId) {
        this.clientId = clientId;
        return this;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Boolean isHasExtraData() {
        return hasExtraData;
    }

    public CarerServiceUserRelation hasExtraData(Boolean hasExtraData) {
        this.hasExtraData = hasExtraData;
        return this;
    }

    public void setHasExtraData(Boolean hasExtraData) {
        this.hasExtraData = hasExtraData;
    }

    public RelationshipType getRelationType() {
        return relationType;
    }

    public CarerServiceUserRelation relationType(RelationshipType relationshipType) {
        this.relationType = relationshipType;
        return this;
    }

    public void setRelationType(RelationshipType relationshipType) {
        this.relationType = relationshipType;
    }

    public Employee getEmployee() {
        return employee;
    }

    public CarerServiceUserRelation employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public ServiceUser getServiceUser() {
        return serviceUser;
    }

    public CarerServiceUserRelation serviceUser(ServiceUser serviceUser) {
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
        if (!(o instanceof CarerServiceUserRelation)) {
            return false;
        }
        return id != null && id.equals(((CarerServiceUserRelation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CarerServiceUserRelation{" +
            "id=" + getId() +
            ", reason='" + getReason() + "'" +
            ", count=" + getCount() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", clientId=" + getClientId() +
            ", hasExtraData='" + isHasExtraData() + "'" +
            "}";
    }
}
