package com.amc.careplanner.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Consent.
 */
@Entity
@Table(name = "consent")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Consent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "give_consent")
    private Boolean giveConsent;

    @Column(name = "arrangements")
    private String arrangements;

    @Column(name = "service_user_signature")
    private String serviceUserSignature;

    @Lob
    @Column(name = "signature_image")
    private byte[] signatureImage;

    @Column(name = "signature_image_content_type")
    private String signatureImageContentType;

    @Column(name = "signature_image_url")
    private String signatureImageUrl;

    @Column(name = "consent_date")
    private ZonedDateTime consentDate;

    @Column(name = "last_updated_date")
    private ZonedDateTime lastUpdatedDate;

    @NotNull
    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @ManyToOne
    @JsonIgnoreProperties(value = "consents", allowSetters = true)
    private ServiceUser serviceUser;

    @ManyToOne
    @JsonIgnoreProperties(value = "consents", allowSetters = true)
    private Employee witnessedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Consent title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Consent description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isGiveConsent() {
        return giveConsent;
    }

    public Consent giveConsent(Boolean giveConsent) {
        this.giveConsent = giveConsent;
        return this;
    }

    public void setGiveConsent(Boolean giveConsent) {
        this.giveConsent = giveConsent;
    }

    public String getArrangements() {
        return arrangements;
    }

    public Consent arrangements(String arrangements) {
        this.arrangements = arrangements;
        return this;
    }

    public void setArrangements(String arrangements) {
        this.arrangements = arrangements;
    }

    public String getServiceUserSignature() {
        return serviceUserSignature;
    }

    public Consent serviceUserSignature(String serviceUserSignature) {
        this.serviceUserSignature = serviceUserSignature;
        return this;
    }

    public void setServiceUserSignature(String serviceUserSignature) {
        this.serviceUserSignature = serviceUserSignature;
    }

    public byte[] getSignatureImage() {
        return signatureImage;
    }

    public Consent signatureImage(byte[] signatureImage) {
        this.signatureImage = signatureImage;
        return this;
    }

    public void setSignatureImage(byte[] signatureImage) {
        this.signatureImage = signatureImage;
    }

    public String getSignatureImageContentType() {
        return signatureImageContentType;
    }

    public Consent signatureImageContentType(String signatureImageContentType) {
        this.signatureImageContentType = signatureImageContentType;
        return this;
    }

    public void setSignatureImageContentType(String signatureImageContentType) {
        this.signatureImageContentType = signatureImageContentType;
    }

    public String getSignatureImageUrl() {
        return signatureImageUrl;
    }

    public Consent signatureImageUrl(String signatureImageUrl) {
        this.signatureImageUrl = signatureImageUrl;
        return this;
    }

    public void setSignatureImageUrl(String signatureImageUrl) {
        this.signatureImageUrl = signatureImageUrl;
    }

    public ZonedDateTime getConsentDate() {
        return consentDate;
    }

    public Consent consentDate(ZonedDateTime consentDate) {
        this.consentDate = consentDate;
        return this;
    }

    public void setConsentDate(ZonedDateTime consentDate) {
        this.consentDate = consentDate;
    }

    public ZonedDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public Consent lastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
        return this;
    }

    public void setLastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Long getClientId() {
        return clientId;
    }

    public Consent clientId(Long clientId) {
        this.clientId = clientId;
        return this;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public ServiceUser getServiceUser() {
        return serviceUser;
    }

    public Consent serviceUser(ServiceUser serviceUser) {
        this.serviceUser = serviceUser;
        return this;
    }

    public void setServiceUser(ServiceUser serviceUser) {
        this.serviceUser = serviceUser;
    }

    public Employee getWitnessedBy() {
        return witnessedBy;
    }

    public Consent witnessedBy(Employee employee) {
        this.witnessedBy = employee;
        return this;
    }

    public void setWitnessedBy(Employee employee) {
        this.witnessedBy = employee;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Consent)) {
            return false;
        }
        return id != null && id.equals(((Consent) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Consent{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", giveConsent='" + isGiveConsent() + "'" +
            ", arrangements='" + getArrangements() + "'" +
            ", serviceUserSignature='" + getServiceUserSignature() + "'" +
            ", signatureImage='" + getSignatureImage() + "'" +
            ", signatureImageContentType='" + getSignatureImageContentType() + "'" +
            ", signatureImageUrl='" + getSignatureImageUrl() + "'" +
            ", consentDate='" + getConsentDate() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", clientId=" + getClientId() +
            "}";
    }
}
