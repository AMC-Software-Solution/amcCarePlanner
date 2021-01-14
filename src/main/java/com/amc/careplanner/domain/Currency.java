package com.amc.careplanner.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Currency.
 */
@Entity
@Table(name = "currency")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Currency implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 99)
    @Column(name = "currency", length = 99, nullable = false)
    private String currency;

    @Size(max = 3)
    @Column(name = "currency_iso_code", length = 3)
    private String currencyIsoCode;

    @Column(name = "currency_symbol")
    private String currencySymbol;

    @Lob
    @Column(name = "currency_logo")
    private byte[] currencyLogo;

    @Column(name = "currency_logo_content_type")
    private String currencyLogoContentType;

    @Column(name = "currency_logo_url")
    private String currencyLogoUrl;

    @Column(name = "has_extra_data")
    private Boolean hasExtraData;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public Currency currency(String currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrencyIsoCode() {
        return currencyIsoCode;
    }

    public Currency currencyIsoCode(String currencyIsoCode) {
        this.currencyIsoCode = currencyIsoCode;
        return this;
    }

    public void setCurrencyIsoCode(String currencyIsoCode) {
        this.currencyIsoCode = currencyIsoCode;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public Currency currencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
        return this;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public byte[] getCurrencyLogo() {
        return currencyLogo;
    }

    public Currency currencyLogo(byte[] currencyLogo) {
        this.currencyLogo = currencyLogo;
        return this;
    }

    public void setCurrencyLogo(byte[] currencyLogo) {
        this.currencyLogo = currencyLogo;
    }

    public String getCurrencyLogoContentType() {
        return currencyLogoContentType;
    }

    public Currency currencyLogoContentType(String currencyLogoContentType) {
        this.currencyLogoContentType = currencyLogoContentType;
        return this;
    }

    public void setCurrencyLogoContentType(String currencyLogoContentType) {
        this.currencyLogoContentType = currencyLogoContentType;
    }

    public String getCurrencyLogoUrl() {
        return currencyLogoUrl;
    }

    public Currency currencyLogoUrl(String currencyLogoUrl) {
        this.currencyLogoUrl = currencyLogoUrl;
        return this;
    }

    public void setCurrencyLogoUrl(String currencyLogoUrl) {
        this.currencyLogoUrl = currencyLogoUrl;
    }

    public Boolean isHasExtraData() {
        return hasExtraData;
    }

    public Currency hasExtraData(Boolean hasExtraData) {
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
        if (!(o instanceof Currency)) {
            return false;
        }
        return id != null && id.equals(((Currency) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Currency{" +
            "id=" + getId() +
            ", currency='" + getCurrency() + "'" +
            ", currencyIsoCode='" + getCurrencyIsoCode() + "'" +
            ", currencySymbol='" + getCurrencySymbol() + "'" +
            ", currencyLogo='" + getCurrencyLogo() + "'" +
            ", currencyLogoContentType='" + getCurrencyLogoContentType() + "'" +
            ", currencyLogoUrl='" + getCurrencyLogoUrl() + "'" +
            ", hasExtraData='" + isHasExtraData() + "'" +
            "}";
    }
}
