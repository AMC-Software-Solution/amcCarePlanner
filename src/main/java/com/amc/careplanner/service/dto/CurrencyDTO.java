package com.amc.careplanner.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.amc.careplanner.domain.Currency} entity.
 */
public class CurrencyDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 99)
    private String currency;

    @Size(max = 3)
    private String currencyIsoCode;

    private String currencySymbol;

    @Lob
    private byte[] currencyLogo;

    private String currencyLogoContentType;
    private String currencyLogoUrl;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrencyIsoCode() {
        return currencyIsoCode;
    }

    public void setCurrencyIsoCode(String currencyIsoCode) {
        this.currencyIsoCode = currencyIsoCode;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public byte[] getCurrencyLogo() {
        return currencyLogo;
    }

    public void setCurrencyLogo(byte[] currencyLogo) {
        this.currencyLogo = currencyLogo;
    }

    public String getCurrencyLogoContentType() {
        return currencyLogoContentType;
    }

    public void setCurrencyLogoContentType(String currencyLogoContentType) {
        this.currencyLogoContentType = currencyLogoContentType;
    }

    public String getCurrencyLogoUrl() {
        return currencyLogoUrl;
    }

    public void setCurrencyLogoUrl(String currencyLogoUrl) {
        this.currencyLogoUrl = currencyLogoUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CurrencyDTO)) {
            return false;
        }

        return id != null && id.equals(((CurrencyDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CurrencyDTO{" +
            "id=" + getId() +
            ", currency='" + getCurrency() + "'" +
            ", currencyIsoCode='" + getCurrencyIsoCode() + "'" +
            ", currencySymbol='" + getCurrencySymbol() + "'" +
            ", currencyLogo='" + getCurrencyLogo() + "'" +
            ", currencyLogoUrl='" + getCurrencyLogoUrl() + "'" +
            "}";
    }
}
