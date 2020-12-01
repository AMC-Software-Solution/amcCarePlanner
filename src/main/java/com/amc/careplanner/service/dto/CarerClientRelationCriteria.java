package com.amc.careplanner.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.amc.careplanner.domain.enumeration.RelationType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.amc.careplanner.domain.CarerClientRelation} entity. This class is used
 * in {@link com.amc.careplanner.web.rest.CarerClientRelationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /carer-client-relations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CarerClientRelationCriteria implements Serializable, Criteria {
    /**
     * Class for filtering RelationType
     */
    public static class RelationTypeFilter extends Filter<RelationType> {

        public RelationTypeFilter() {
        }

        public RelationTypeFilter(RelationTypeFilter filter) {
            super(filter);
        }

        @Override
        public RelationTypeFilter copy() {
            return new RelationTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private RelationTypeFilter relationType;

    private StringFilter reason;

    private LongFilter count;

    private ZonedDateTimeFilter lastUpdatedDate;

    private LongFilter clientId;

    private LongFilter employeeId;

    private LongFilter serviceUserId;

    public CarerClientRelationCriteria() {
    }

    public CarerClientRelationCriteria(CarerClientRelationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.relationType = other.relationType == null ? null : other.relationType.copy();
        this.reason = other.reason == null ? null : other.reason.copy();
        this.count = other.count == null ? null : other.count.copy();
        this.lastUpdatedDate = other.lastUpdatedDate == null ? null : other.lastUpdatedDate.copy();
        this.clientId = other.clientId == null ? null : other.clientId.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.serviceUserId = other.serviceUserId == null ? null : other.serviceUserId.copy();
    }

    @Override
    public CarerClientRelationCriteria copy() {
        return new CarerClientRelationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public RelationTypeFilter getRelationType() {
        return relationType;
    }

    public void setRelationType(RelationTypeFilter relationType) {
        this.relationType = relationType;
    }

    public StringFilter getReason() {
        return reason;
    }

    public void setReason(StringFilter reason) {
        this.reason = reason;
    }

    public LongFilter getCount() {
        return count;
    }

    public void setCount(LongFilter count) {
        this.count = count;
    }

    public ZonedDateTimeFilter getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(ZonedDateTimeFilter lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public LongFilter getClientId() {
        return clientId;
    }

    public void setClientId(LongFilter clientId) {
        this.clientId = clientId;
    }

    public LongFilter getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(LongFilter employeeId) {
        this.employeeId = employeeId;
    }

    public LongFilter getServiceUserId() {
        return serviceUserId;
    }

    public void setServiceUserId(LongFilter serviceUserId) {
        this.serviceUserId = serviceUserId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CarerClientRelationCriteria that = (CarerClientRelationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(relationType, that.relationType) &&
            Objects.equals(reason, that.reason) &&
            Objects.equals(count, that.count) &&
            Objects.equals(lastUpdatedDate, that.lastUpdatedDate) &&
            Objects.equals(clientId, that.clientId) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(serviceUserId, that.serviceUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        relationType,
        reason,
        count,
        lastUpdatedDate,
        clientId,
        employeeId,
        serviceUserId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CarerClientRelationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (relationType != null ? "relationType=" + relationType + ", " : "") +
                (reason != null ? "reason=" + reason + ", " : "") +
                (count != null ? "count=" + count + ", " : "") +
                (lastUpdatedDate != null ? "lastUpdatedDate=" + lastUpdatedDate + ", " : "") +
                (clientId != null ? "clientId=" + clientId + ", " : "") +
                (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
                (serviceUserId != null ? "serviceUserId=" + serviceUserId + ", " : "") +
            "}";
    }

}
