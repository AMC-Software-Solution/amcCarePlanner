package com.amc.careplanner.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;

/**
 * A DTO for the {@link com.amc.careplanner.domain.QuestionType} entity.
 */
public class QuestionTypeDTO implements Serializable {
    
    private Long id;

    private String questionType;

    private ZonedDateTime lastUpdatedDate;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public ZonedDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuestionTypeDTO)) {
            return false;
        }

        return id != null && id.equals(((QuestionTypeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuestionTypeDTO{" +
            "id=" + getId() +
            ", questionType='" + getQuestionType() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            "}";
    }
}
