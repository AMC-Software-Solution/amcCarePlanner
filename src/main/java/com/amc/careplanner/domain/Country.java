package com.amc.careplanner.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Country.
 */
@Entity
@Table(name = "country")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "country_name", length = 100, nullable = false)
    private String countryName;

    @Size(max = 6)
    @Column(name = "country_iso_code", length = 6)
    private String countryIsoCode;

    @Column(name = "country_flag_url")
    private String countryFlagUrl;

    @Column(name = "country_calling_code")
    private String countryCallingCode;

    @Column(name = "country_tel_digit_length")
    private Integer countryTelDigitLength;

    @Column(name = "last_updated_date")
    private ZonedDateTime lastUpdatedDate;

    @NotNull
    @Column(name = "tenant_id", nullable = false)
    private Long tenantId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public Country countryName(String countryName) {
        this.countryName = countryName;
        return this;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryIsoCode() {
        return countryIsoCode;
    }

    public Country countryIsoCode(String countryIsoCode) {
        this.countryIsoCode = countryIsoCode;
        return this;
    }

    public void setCountryIsoCode(String countryIsoCode) {
        this.countryIsoCode = countryIsoCode;
    }

    public String getCountryFlagUrl() {
        return countryFlagUrl;
    }

    public Country countryFlagUrl(String countryFlagUrl) {
        this.countryFlagUrl = countryFlagUrl;
        return this;
    }

    public void setCountryFlagUrl(String countryFlagUrl) {
        this.countryFlagUrl = countryFlagUrl;
    }

    public String getCountryCallingCode() {
        return countryCallingCode;
    }

    public Country countryCallingCode(String countryCallingCode) {
        this.countryCallingCode = countryCallingCode;
        return this;
    }

    public void setCountryCallingCode(String countryCallingCode) {
        this.countryCallingCode = countryCallingCode;
    }

    public Integer getCountryTelDigitLength() {
        return countryTelDigitLength;
    }

    public Country countryTelDigitLength(Integer countryTelDigitLength) {
        this.countryTelDigitLength = countryTelDigitLength;
        return this;
    }

    public void setCountryTelDigitLength(Integer countryTelDigitLength) {
        this.countryTelDigitLength = countryTelDigitLength;
    }

    public ZonedDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public Country lastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
        return this;
    }

    public void setLastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public Country tenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Country)) {
            return false;
        }
        return id != null && id.equals(((Country) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Country{" +
            "id=" + getId() +
            ", countryName='" + getCountryName() + "'" +
            ", countryIsoCode='" + getCountryIsoCode() + "'" +
            ", countryFlagUrl='" + getCountryFlagUrl() + "'" +
            ", countryCallingCode='" + getCountryCallingCode() + "'" +
            ", countryTelDigitLength=" + getCountryTelDigitLength() +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", tenantId=" + getTenantId() +
            "}";
    }
}
