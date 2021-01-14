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
 * Criteria class for the {@link com.amc.careplanner.domain.RelationshipType} entity. This class is used
 * in {@link com.amc.careplanner.web.rest.RelationshipTypeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /relationship-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RelationshipTypeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter relationType;

    private StringFilter description;

    private LongFilter clientId;

    private BooleanFilter hasExtraData;

    public RelationshipTypeCriteria() {
    }

    public RelationshipTypeCriteria(RelationshipTypeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.relationType = other.relationType == null ? null : other.relationType.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.clientId = other.clientId == null ? null : other.clientId.copy();
        this.hasExtraData = other.hasExtraData == null ? null : other.hasExtraData.copy();
    }

    @Override
    public RelationshipTypeCriteria copy() {
        return new RelationshipTypeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getRelationType() {
        return relationType;
    }

    public void setRelationType(StringFilter relationType) {
        this.relationType = relationType;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public LongFilter getClientId() {
        return clientId;
    }

    public void setClientId(LongFilter clientId) {
        this.clientId = clientId;
    }

    public BooleanFilter getHasExtraData() {
        return hasExtraData;
    }

    public void setHasExtraData(BooleanFilter hasExtraData) {
        this.hasExtraData = hasExtraData;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RelationshipTypeCriteria that = (RelationshipTypeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(relationType, that.relationType) &&
            Objects.equals(description, that.description) &&
            Objects.equals(clientId, that.clientId) &&
            Objects.equals(hasExtraData, that.hasExtraData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        relationType,
        description,
        clientId,
        hasExtraData
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RelationshipTypeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (relationType != null ? "relationType=" + relationType + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (clientId != null ? "clientId=" + clientId + ", " : "") +
                (hasExtraData != null ? "hasExtraData=" + hasExtraData + ", " : "") +
            "}";
    }

}
