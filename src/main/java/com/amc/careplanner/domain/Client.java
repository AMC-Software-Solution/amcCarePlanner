package com.amc.careplanner.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "client_name", nullable = false)
    private String clientName;

    @Column(name = "client_description")
    private String clientDescription;

    @Lob
    @Column(name = "client_logo")
    private byte[] clientLogo;

    @Column(name = "client_logo_content_type")
    private String clientLogoContentType;

    @Column(name = "client_logo_url")
    private String clientLogoUrl;

    @Column(name = "client_contact_name")
    private String clientContactName;

    @Column(name = "client_phone")
    private String clientPhone;

    @Column(name = "client_email")
    private String clientEmail;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "reason")
    private String reason;

    @Column(name = "last_updated_date")
    private ZonedDateTime lastUpdatedDate;

    @Column(name = "has_extra_data")
    private Boolean hasExtraData;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public Client clientName(String clientName) {
        this.clientName = clientName;
        return this;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientDescription() {
        return clientDescription;
    }

    public Client clientDescription(String clientDescription) {
        this.clientDescription = clientDescription;
        return this;
    }

    public void setClientDescription(String clientDescription) {
        this.clientDescription = clientDescription;
    }

    public byte[] getClientLogo() {
        return clientLogo;
    }

    public Client clientLogo(byte[] clientLogo) {
        this.clientLogo = clientLogo;
        return this;
    }

    public void setClientLogo(byte[] clientLogo) {
        this.clientLogo = clientLogo;
    }

    public String getClientLogoContentType() {
        return clientLogoContentType;
    }

    public Client clientLogoContentType(String clientLogoContentType) {
        this.clientLogoContentType = clientLogoContentType;
        return this;
    }

    public void setClientLogoContentType(String clientLogoContentType) {
        this.clientLogoContentType = clientLogoContentType;
    }

    public String getClientLogoUrl() {
        return clientLogoUrl;
    }

    public Client clientLogoUrl(String clientLogoUrl) {
        this.clientLogoUrl = clientLogoUrl;
        return this;
    }

    public void setClientLogoUrl(String clientLogoUrl) {
        this.clientLogoUrl = clientLogoUrl;
    }

    public String getClientContactName() {
        return clientContactName;
    }

    public Client clientContactName(String clientContactName) {
        this.clientContactName = clientContactName;
        return this;
    }

    public void setClientContactName(String clientContactName) {
        this.clientContactName = clientContactName;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public Client clientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
        return this;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public Client clientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
        return this;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public Client createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public Client enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getReason() {
        return reason;
    }

    public Client reason(String reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ZonedDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public Client lastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
        return this;
    }

    public void setLastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Boolean isHasExtraData() {
        return hasExtraData;
    }

    public Client hasExtraData(Boolean hasExtraData) {
        this.hasExtraData = hasExtraData;
        return this;
    }

    public void setHasExtraData(Boolean hasExtraData) {
        this.hasExtraData = hasExtraData;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return id != null && id.equals(((Client) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", clientName='" + getClientName() + "'" +
            ", clientDescription='" + getClientDescription() + "'" +
            ", clientLogo='" + getClientLogo() + "'" +
            ", clientLogoContentType='" + getClientLogoContentType() + "'" +
            ", clientLogoUrl='" + getClientLogoUrl() + "'" +
            ", clientContactName='" + getClientContactName() + "'" +
            ", clientPhone='" + getClientPhone() + "'" +
            ", clientEmail='" + getClientEmail() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", enabled='" + isEnabled() + "'" +
            ", reason='" + getReason() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", hasExtraData='" + isHasExtraData() + "'" +
            "}";
    }
}
