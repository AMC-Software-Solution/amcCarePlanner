package com.amc.careplanner.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A EligibilityType.
 */
@Entity
@Table(name = "eligibility_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EligibilityType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "eligibility_type", nullable = false)
    private String eligibilityType;

    @Column(name = "description")
    private String description;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEligibilityType() {
        return eligibilityType;
    }

    public EligibilityType eligibilityType(String eligibilityType) {
        this.eligibilityType = eligibilityType;
        return this;
    }

    public void setEligibilityType(String eligibilityType) {
        this.eligibilityType = eligibilityType;
    }

    public String getDescription() {
        return description;
    }

    public EligibilityType description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EligibilityType)) {
            return false;
        }
        return id != null && id.equals(((EligibilityType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EligibilityType{" +
            "id=" + getId() +
            ", eligibilityType='" + getEligibilityType() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
