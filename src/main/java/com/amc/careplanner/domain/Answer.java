package com.amc.careplanner.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Answer.
 */
@Entity
@Table(name = "answer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Answer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "answer")
    private String answer;

    @Column(name = "description")
    private String description;

    @Column(name = "attribute_1")
    private String attribute1;

    @Column(name = "attribute_2")
    private String attribute2;

    @Column(name = "attribute_3")
    private String attribute3;

    @Column(name = "attribute_4")
    private String attribute4;

    @Column(name = "attribute_5")
    private String attribute5;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "last_updated_date")
    private ZonedDateTime lastUpdatedDate;

    @NotNull
    @Column(name = "tenant_id", nullable = false)
    private Long tenantId;

    @ManyToOne
    @JsonIgnoreProperties(value = "answers", allowSetters = true)
    private Question question;

    @ManyToOne
    @JsonIgnoreProperties(value = "answers", allowSetters = true)
    private ServiceUser serviceUser;

    @ManyToOne
    @JsonIgnoreProperties(value = "answers", allowSetters = true)
    private Employee recordedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public Answer answer(String answer) {
        this.answer = answer;
        return this;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getDescription() {
        return description;
    }

    public Answer description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAttribute1() {
        return attribute1;
    }

    public Answer attribute1(String attribute1) {
        this.attribute1 = attribute1;
        return this;
    }

    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }

    public String getAttribute2() {
        return attribute2;
    }

    public Answer attribute2(String attribute2) {
        this.attribute2 = attribute2;
        return this;
    }

    public void setAttribute2(String attribute2) {
        this.attribute2 = attribute2;
    }

    public String getAttribute3() {
        return attribute3;
    }

    public Answer attribute3(String attribute3) {
        this.attribute3 = attribute3;
        return this;
    }

    public void setAttribute3(String attribute3) {
        this.attribute3 = attribute3;
    }

    public String getAttribute4() {
        return attribute4;
    }

    public Answer attribute4(String attribute4) {
        this.attribute4 = attribute4;
        return this;
    }

    public void setAttribute4(String attribute4) {
        this.attribute4 = attribute4;
    }

    public String getAttribute5() {
        return attribute5;
    }

    public Answer attribute5(String attribute5) {
        this.attribute5 = attribute5;
        return this;
    }

    public void setAttribute5(String attribute5) {
        this.attribute5 = attribute5;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public Answer createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public Answer lastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
        return this;
    }

    public void setLastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public Answer tenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Question getQuestion() {
        return question;
    }

    public Answer question(Question question) {
        this.question = question;
        return this;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public ServiceUser getServiceUser() {
        return serviceUser;
    }

    public Answer serviceUser(ServiceUser serviceUser) {
        this.serviceUser = serviceUser;
        return this;
    }

    public void setServiceUser(ServiceUser serviceUser) {
        this.serviceUser = serviceUser;
    }

    public Employee getRecordedBy() {
        return recordedBy;
    }

    public Answer recordedBy(Employee employee) {
        this.recordedBy = employee;
        return this;
    }

    public void setRecordedBy(Employee employee) {
        this.recordedBy = employee;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Answer)) {
            return false;
        }
        return id != null && id.equals(((Answer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Answer{" +
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
            "}";
    }
}
