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
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.amc.careplanner.domain.Answer} entity. This class is used
 * in {@link com.amc.careplanner.web.rest.AnswerResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /answers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AnswerCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter answer;

    private StringFilter description;

    private StringFilter attribute1;

    private StringFilter attribute2;

    private StringFilter attribute3;

    private StringFilter attribute4;

    private StringFilter attribute5;

    private ZonedDateTimeFilter createdDate;

    private ZonedDateTimeFilter lastUpdatedDate;

    private LongFilter clientId;

    private LongFilter questionId;

    private LongFilter serviceUserId;

    private LongFilter recordedById;

    public AnswerCriteria() {
    }

    public AnswerCriteria(AnswerCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.answer = other.answer == null ? null : other.answer.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.attribute1 = other.attribute1 == null ? null : other.attribute1.copy();
        this.attribute2 = other.attribute2 == null ? null : other.attribute2.copy();
        this.attribute3 = other.attribute3 == null ? null : other.attribute3.copy();
        this.attribute4 = other.attribute4 == null ? null : other.attribute4.copy();
        this.attribute5 = other.attribute5 == null ? null : other.attribute5.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.lastUpdatedDate = other.lastUpdatedDate == null ? null : other.lastUpdatedDate.copy();
        this.clientId = other.clientId == null ? null : other.clientId.copy();
        this.questionId = other.questionId == null ? null : other.questionId.copy();
        this.serviceUserId = other.serviceUserId == null ? null : other.serviceUserId.copy();
        this.recordedById = other.recordedById == null ? null : other.recordedById.copy();
    }

    @Override
    public AnswerCriteria copy() {
        return new AnswerCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getAnswer() {
        return answer;
    }

    public void setAnswer(StringFilter answer) {
        this.answer = answer;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(StringFilter attribute1) {
        this.attribute1 = attribute1;
    }

    public StringFilter getAttribute2() {
        return attribute2;
    }

    public void setAttribute2(StringFilter attribute2) {
        this.attribute2 = attribute2;
    }

    public StringFilter getAttribute3() {
        return attribute3;
    }

    public void setAttribute3(StringFilter attribute3) {
        this.attribute3 = attribute3;
    }

    public StringFilter getAttribute4() {
        return attribute4;
    }

    public void setAttribute4(StringFilter attribute4) {
        this.attribute4 = attribute4;
    }

    public StringFilter getAttribute5() {
        return attribute5;
    }

    public void setAttribute5(StringFilter attribute5) {
        this.attribute5 = attribute5;
    }

    public ZonedDateTimeFilter getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTimeFilter createdDate) {
        this.createdDate = createdDate;
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

    public LongFilter getQuestionId() {
        return questionId;
    }

    public void setQuestionId(LongFilter questionId) {
        this.questionId = questionId;
    }

    public LongFilter getServiceUserId() {
        return serviceUserId;
    }

    public void setServiceUserId(LongFilter serviceUserId) {
        this.serviceUserId = serviceUserId;
    }

    public LongFilter getRecordedById() {
        return recordedById;
    }

    public void setRecordedById(LongFilter recordedById) {
        this.recordedById = recordedById;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AnswerCriteria that = (AnswerCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(answer, that.answer) &&
            Objects.equals(description, that.description) &&
            Objects.equals(attribute1, that.attribute1) &&
            Objects.equals(attribute2, that.attribute2) &&
            Objects.equals(attribute3, that.attribute3) &&
            Objects.equals(attribute4, that.attribute4) &&
            Objects.equals(attribute5, that.attribute5) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastUpdatedDate, that.lastUpdatedDate) &&
            Objects.equals(clientId, that.clientId) &&
            Objects.equals(questionId, that.questionId) &&
            Objects.equals(serviceUserId, that.serviceUserId) &&
            Objects.equals(recordedById, that.recordedById);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        answer,
        description,
        attribute1,
        attribute2,
        attribute3,
        attribute4,
        attribute5,
        createdDate,
        lastUpdatedDate,
        clientId,
        questionId,
        serviceUserId,
        recordedById
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnswerCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (answer != null ? "answer=" + answer + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (attribute1 != null ? "attribute1=" + attribute1 + ", " : "") +
                (attribute2 != null ? "attribute2=" + attribute2 + ", " : "") +
                (attribute3 != null ? "attribute3=" + attribute3 + ", " : "") +
                (attribute4 != null ? "attribute4=" + attribute4 + ", " : "") +
                (attribute5 != null ? "attribute5=" + attribute5 + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastUpdatedDate != null ? "lastUpdatedDate=" + lastUpdatedDate + ", " : "") +
                (clientId != null ? "clientId=" + clientId + ", " : "") +
                (questionId != null ? "questionId=" + questionId + ", " : "") +
                (serviceUserId != null ? "serviceUserId=" + serviceUserId + ", " : "") +
                (recordedById != null ? "recordedById=" + recordedById + ", " : "") +
            "}";
    }

}
