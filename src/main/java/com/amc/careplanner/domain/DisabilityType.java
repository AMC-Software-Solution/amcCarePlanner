package com.amc.careplanner.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A DisabilityType.
 */
@Entity
@Table(name = "disability_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DisabilityType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "disability", nullable = false)
    private String disability;

    @Column(name = "description")
    private String description;

    @Column(name = "has_extra_data")
    private Boolean hasExtraData;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisability() {
        return disability;
    }

    public DisabilityType disability(String disability) {
        this.disability = disability;
        return this;
    }

    public void setDisability(String disability) {
        this.disability = disability;
    }

    public String getDescription() {
        return description;
    }

    public DisabilityType description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isHasExtraData() {
        return hasExtraData;
    }

    public DisabilityType hasExtraData(Boolean hasExtraData) {
        this.hasExtraData = hasExtraData;
        return this;
    }

    public void setHasExtraData(Boolean hasExtraData) {
        this.hasExtraData = hasExtraData;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DisabilityType)) {
            return false;
        }
        return id != null && id.equals(((DisabilityType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DisabilityType{" +
            "id=" + getId() +
            ", disability='" + getDisability() + "'" +
            ", description='" + getDescription() + "'" +
            ", hasExtraData='" + isHasExtraData() + "'" +
            "}";
    }
}
