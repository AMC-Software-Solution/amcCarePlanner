package com.amc.careplanner.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.amc.careplanner.domain.EligibilityType} entity. This class is used
 * in {@link com.amc.careplanner.web.rest.EligibilityTypeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /eligibility-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EligibilityTypeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter eligibilityType;

    private StringFilter description;

    public EligibilityTypeCriteria() {
    }

    public EligibilityTypeCriteria(EligibilityTypeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.eligibilityType = other.eligibilityType == null ? null : other.eligibilityType.copy();
        this.description = other.description == null ? null : other.description.copy();
    }

    @Override
    public EligibilityTypeCriteria copy() {
        return new EligibilityTypeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getEligibilityType() {
        return eligibilityType;
    }

    public void setEligibilityType(StringFilter eligibilityType) {
        this.eligibilityType = eligibilityType;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EligibilityTypeCriteria that = (EligibilityTypeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(eligibilityType, that.eligibilityType) &&
            Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        eligibilityType,
        description
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EligibilityTypeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (eligibilityType != null ? "eligibilityType=" + eligibilityType + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
            "}";
    }

}
