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

/**
 * Criteria class for the {@link com.amc.careplanner.domain.Currency} entity. This class is used
 * in {@link com.amc.careplanner.web.rest.CurrencyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /currencies?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CurrencyCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter currency;

    private StringFilter currencyIsoCode;

    private StringFilter currencySymbol;

    private StringFilter currencyLogoUrl;

    public CurrencyCriteria() {
    }

    public CurrencyCriteria(CurrencyCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.currency = other.currency == null ? null : other.currency.copy();
        this.currencyIsoCode = other.currencyIsoCode == null ? null : other.currencyIsoCode.copy();
        this.currencySymbol = other.currencySymbol == null ? null : other.currencySymbol.copy();
        this.currencyLogoUrl = other.currencyLogoUrl == null ? null : other.currencyLogoUrl.copy();
    }

    @Override
    public CurrencyCriteria copy() {
        return new CurrencyCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCurrency() {
        return currency;
    }

    public void setCurrency(StringFilter currency) {
        this.currency = currency;
    }

    public StringFilter getCurrencyIsoCode() {
        return currencyIsoCode;
    }

    public void setCurrencyIsoCode(StringFilter currencyIsoCode) {
        this.currencyIsoCode = currencyIsoCode;
    }

    public StringFilter getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(StringFilter currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public StringFilter getCurrencyLogoUrl() {
        return currencyLogoUrl;
    }

    public void setCurrencyLogoUrl(StringFilter currencyLogoUrl) {
        this.currencyLogoUrl = currencyLogoUrl;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CurrencyCriteria that = (CurrencyCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(currency, that.currency) &&
            Objects.equals(currencyIsoCode, that.currencyIsoCode) &&
            Objects.equals(currencySymbol, that.currencySymbol) &&
            Objects.equals(currencyLogoUrl, that.currencyLogoUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        currency,
        currencyIsoCode,
        currencySymbol,
        currencyLogoUrl
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CurrencyCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (currency != null ? "currency=" + currency + ", " : "") +
                (currencyIsoCode != null ? "currencyIsoCode=" + currencyIsoCode + ", " : "") +
                (currencySymbol != null ? "currencySymbol=" + currencySymbol + ", " : "") +
                (currencyLogoUrl != null ? "currencyLogoUrl=" + currencyLogoUrl + ", " : "") +
            "}";
    }

}
