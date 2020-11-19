package com.amc.careplanner.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

import com.amc.careplanner.domain.enumeration.RelationType;

/**
 * A CarerClientRelation.
 */
@Entity
@Table(name = "carer_client_relation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CarerClientRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "relation_type", nullable = false)
    private RelationType relationType;

    @Column(name = "reason")
    private String reason;

    @Column(name = "count")
    private Long count;

    @Column(name = "last_updated_date")
    private ZonedDateTime lastUpdatedDate;

    @NotNull
    @Column(name = "tenant_id", nullable = false)
    private Long tenantId;

    @ManyToOne
    @JsonIgnoreProperties(value = "carerClientRelations", allowSetters = true)
    private Employee employee;

    @ManyToOne
    @JsonIgnoreProperties(value = "carerClientRelations", allowSetters = true)
    private ServiceUser serviceUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RelationType getRelationType() {
        return relationType;
    }

    public CarerClientRelation relationType(RelationType relationType) {
        this.relationType = relationType;
        return this;
    }

    public void setRelationType(RelationType relationType) {
        this.relationType = relationType;
    }

    public String getReason() {
        return reason;
    }

    public CarerClientRelation reason(String reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getCount() {
        return count;
    }

    public CarerClientRelation count(Long count) {
        this.count = count;
        return this;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public ZonedDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public CarerClientRelation lastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
        return this;
    }

    public void setLastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public CarerClientRelation tenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public CarerClientRelation employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public ServiceUser getServiceUser() {
        return serviceUser;
    }

    public CarerClientRelation serviceUser(ServiceUser serviceUser) {
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
        if (!(o instanceof CarerClientRelation)) {
            return false;
        }
        return id != null && id.equals(((CarerClientRelation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CarerClientRelation{" +
            "id=" + getId() +
            ", relationType='" + getRelationType() + "'" +
            ", reason='" + getReason() + "'" +
            ", count=" + getCount() +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", tenantId=" + getTenantId() +
            "}";
    }
}
