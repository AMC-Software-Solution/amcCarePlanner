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
 * Criteria class for the {@link com.amc.careplanner.domain.DisabilityType} entity. This class is used
 * in {@link com.amc.careplanner.web.rest.DisabilityTypeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /disability-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DisabilityTypeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter disability;

    private StringFilter description;

    public DisabilityTypeCriteria() {
    }

    public DisabilityTypeCriteria(DisabilityTypeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.disability = other.disability == null ? null : other.disability.copy();
        this.description = other.description == null ? null : other.description.copy();
    }

    @Override
    public DisabilityTypeCriteria copy() {
        return new DisabilityTypeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDisability() {
        return disability;
    }

    public void setDisability(StringFilter disability) {
        this.disability = disability;
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
        final DisabilityTypeCriteria that = (DisabilityTypeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(disability, that.disability) &&
            Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        disability,
        description
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DisabilityTypeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (disability != null ? "disability=" + disability + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
            "}";
    }

}
