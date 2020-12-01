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
 * Criteria class for the {@link com.amc.careplanner.domain.QuestionType} entity. This class is used
 * in {@link com.amc.careplanner.web.rest.QuestionTypeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /question-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class QuestionTypeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter questionType;

    private ZonedDateTimeFilter lastUpdatedDate;

    public QuestionTypeCriteria() {
    }

    public QuestionTypeCriteria(QuestionTypeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.questionType = other.questionType == null ? null : other.questionType.copy();
        this.lastUpdatedDate = other.lastUpdatedDate == null ? null : other.lastUpdatedDate.copy();
    }

    @Override
    public QuestionTypeCriteria copy() {
        return new QuestionTypeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getQuestionType() {
        return questionType;
    }

    public void setQuestionType(StringFilter questionType) {
        this.questionType = questionType;
    }

    public ZonedDateTimeFilter getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(ZonedDateTimeFilter lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final QuestionTypeCriteria that = (QuestionTypeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(questionType, that.questionType) &&
            Objects.equals(lastUpdatedDate, that.lastUpdatedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        questionType,
        lastUpdatedDate
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuestionTypeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (questionType != null ? "questionType=" + questionType + ", " : "") +
                (lastUpdatedDate != null ? "lastUpdatedDate=" + lastUpdatedDate + ", " : "") +
            "}";
    }

}
