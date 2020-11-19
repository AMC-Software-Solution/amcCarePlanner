package com.amc.careplanner.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.amc.careplanner.domain.Answer} entity.
 */
public class AnswerDTO implements Serializable {
    
    private Long id;

    private String answer;

    private String description;

    private String attribute1;

    private String attribute2;

    private String attribute3;

    private String attribute4;

    private String attribute5;

    private ZonedDateTime createdDate;

    private ZonedDateTime lastUpdatedDate;

    @NotNull
    private Long tenantId;


    private Long questionId;

    private String questionQuestion;

    private Long serviceUserId;

    private String serviceUserServiceUserCode;

    private Long recordedById;

    private String recordedByEmployeeCode;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }

    public String getAttribute2() {
        return attribute2;
    }

    public void setAttribute2(String attribute2) {
        this.attribute2 = attribute2;
    }

    public String getAttribute3() {
        return attribute3;
    }

    public void setAttribute3(String attribute3) {
        this.attribute3 = attribute3;
    }

    public String getAttribute4() {
        return attribute4;
    }

    public void setAttribute4(String attribute4) {
        this.attribute4 = attribute4;
    }

    public String getAttribute5() {
        return attribute5;
    }

    public void setAttribute5(String attribute5) {
        this.attribute5 = attribute5;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getQuestionQuestion() {
        return questionQuestion;
    }

    public void setQuestionQuestion(String questionQuestion) {
        this.questionQuestion = questionQuestion;
    }

    public Long getServiceUserId() {
        return serviceUserId;
    }

    public void setServiceUserId(Long serviceUserId) {
        this.serviceUserId = serviceUserId;
    }

    public String getServiceUserServiceUserCode() {
        return serviceUserServiceUserCode;
    }

    public void setServiceUserServiceUserCode(String serviceUserServiceUserCode) {
        this.serviceUserServiceUserCode = serviceUserServiceUserCode;
    }

    public Long getRecordedById() {
        return recordedById;
    }

    public void setRecordedById(Long employeeId) {
        this.recordedById = employeeId;
    }

    public String getRecordedByEmployeeCode() {
        return recordedByEmployeeCode;
    }

    public void setRecordedByEmployeeCode(String employeeEmployeeCode) {
        this.recordedByEmployeeCode = employeeEmployeeCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnswerDTO)) {
            return false;
        }

        return id != null && id.equals(((AnswerDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnswerDTO{" +
            "id=" + getId() +
            ", answer='" + getAnswer() + "'" +
            ", description='" + getDescription() + "'" +
            ", attribute1='" + getAttribute1() + "'" +
            ", attribute2='" + getAttribute2() + "'" +
            ", attribute3='" + getAttribute3() + "'" +
            ", attribute4='" + getAttribute4() + "'" +
            ", attribute5='" + getAttribute5() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", tenantId=" + getTenantId() +
            ", questionId=" + getQuestionId() +
            ", questionQuestion='" + getQuestionQuestion() + "'" +
            ", serviceUserId=" + getServiceUserId() +
            ", serviceUserServiceUserCode='" + getServiceUserServiceUserCode() + "'" +
            ", recordedById=" + getRecordedById() +
            ", recordedByEmployeeCode='" + getRecordedByEmployeeCode() + "'" +
            "}";
    }
}
