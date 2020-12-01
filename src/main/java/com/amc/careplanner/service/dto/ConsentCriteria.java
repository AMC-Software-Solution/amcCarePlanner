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
 * Criteria class for the {@link com.amc.careplanner.domain.Consent} entity. This class is used
 * in {@link com.amc.careplanner.web.rest.ConsentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /consents?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ConsentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private StringFilter description;

    private BooleanFilter giveConsent;

    private StringFilter arrangements;

    private StringFilter serviceUserSignature;

    private StringFilter signatureImageUrl;

    private ZonedDateTimeFilter consentDate;

    private ZonedDateTimeFilter lastUpdatedDate;

    private LongFilter clientId;

    private LongFilter serviceUserId;

    private LongFilter witnessedById;

    public ConsentCriteria() {
    }

    public ConsentCriteria(ConsentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.giveConsent = other.giveConsent == null ? null : other.giveConsent.copy();
        this.arrangements = other.arrangements == null ? null : other.arrangements.copy();
        this.serviceUserSignature = other.serviceUserSignature == null ? null : other.serviceUserSignature.copy();
        this.signatureImageUrl = other.signatureImageUrl == null ? null : other.signatureImageUrl.copy();
        this.consentDate = other.consentDate == null ? null : other.consentDate.copy();
        this.lastUpdatedDate = other.lastUpdatedDate == null ? null : other.lastUpdatedDate.copy();
        this.clientId = other.clientId == null ? null : other.clientId.copy();
        this.serviceUserId = other.serviceUserId == null ? null : other.serviceUserId.copy();
        this.witnessedById = other.witnessedById == null ? null : other.witnessedById.copy();
    }

    @Override
    public ConsentCriteria copy() {
        return new ConsentCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitle() {
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public BooleanFilter getGiveConsent() {
        return giveConsent;
    }

    public void setGiveConsent(BooleanFilter giveConsent) {
        this.giveConsent = giveConsent;
    }

    public StringFilter getArrangements() {
        return arrangements;
    }

    public void setArrangements(StringFilter arrangements) {
        this.arrangements = arrangements;
    }

    public StringFilter getServiceUserSignature() {
        return serviceUserSignature;
    }

    public void setServiceUserSignature(StringFilter serviceUserSignature) {
        this.serviceUserSignature = serviceUserSignature;
    }

    public StringFilter getSignatureImageUrl() {
        return signatureImageUrl;
    }

    public void setSignatureImageUrl(StringFilter signatureImageUrl) {
        this.signatureImageUrl = signatureImageUrl;
    }

    public ZonedDateTimeFilter getConsentDate() {
        return consentDate;
    }

    public void setConsentDate(ZonedDateTimeFilter consentDate) {
        this.consentDate = consentDate;
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

    public LongFilter getServiceUserId() {
        return serviceUserId;
    }

    public void setServiceUserId(LongFilter serviceUserId) {
        this.serviceUserId = serviceUserId;
    }

    public LongFilter getWitnessedById() {
        return witnessedById;
    }

    public void setWitnessedById(LongFilter witnessedById) {
        this.witnessedById = witnessedById;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ConsentCriteria that = (ConsentCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(description, that.description) &&
            Objects.equals(giveConsent, that.giveConsent) &&
            Objects.equals(arrangements, that.arrangements) &&
            Objects.equals(serviceUserSignature, that.serviceUserSignature) &&
            Objects.equals(signatureImageUrl, that.signatureImageUrl) &&
            Objects.equals(consentDate, that.consentDate) &&
            Objects.equals(lastUpdatedDate, that.lastUpdatedDate) &&
            Objects.equals(clientId, that.clientId) &&
            Objects.equals(serviceUserId, that.serviceUserId) &&
            Objects.equals(witnessedById, that.witnessedById);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        title,
        description,
        giveConsent,
        arrangements,
        serviceUserSignature,
        signatureImageUrl,
        consentDate,
        lastUpdatedDate,
        clientId,
        serviceUserId,
        witnessedById
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConsentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (giveConsent != null ? "giveConsent=" + giveConsent + ", " : "") +
                (arrangements != null ? "arrangements=" + arrangements + ", " : "") +
                (serviceUserSignature != null ? "serviceUserSignature=" + serviceUserSignature + ", " : "") +
                (signatureImageUrl != null ? "signatureImageUrl=" + signatureImageUrl + ", " : "") +
                (consentDate != null ? "consentDate=" + consentDate + ", " : "") +
                (lastUpdatedDate != null ? "lastUpdatedDate=" + lastUpdatedDate + ", " : "") +
                (clientId != null ? "clientId=" + clientId + ", " : "") +
                (serviceUserId != null ? "serviceUserId=" + serviceUserId + ", " : "") +
                (witnessedById != null ? "witnessedById=" + witnessedById + ", " : "") +
            "}";
    }

}
