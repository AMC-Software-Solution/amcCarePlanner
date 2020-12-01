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
 * Criteria class for the {@link com.amc.careplanner.domain.Country} entity. This class is used
 * in {@link com.amc.careplanner.web.rest.CountryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /countries?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CountryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter countryName;

    private StringFilter countryIsoCode;

    private StringFilter countryFlagUrl;

    private StringFilter countryCallingCode;

    private IntegerFilter countryTelDigitLength;

    private ZonedDateTimeFilter lastUpdatedDate;

    public CountryCriteria() {
    }

    public CountryCriteria(CountryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.countryName = other.countryName == null ? null : other.countryName.copy();
        this.countryIsoCode = other.countryIsoCode == null ? null : other.countryIsoCode.copy();
        this.countryFlagUrl = other.countryFlagUrl == null ? null : other.countryFlagUrl.copy();
        this.countryCallingCode = other.countryCallingCode == null ? null : other.countryCallingCode.copy();
        this.countryTelDigitLength = other.countryTelDigitLength == null ? null : other.countryTelDigitLength.copy();
        this.lastUpdatedDate = other.lastUpdatedDate == null ? null : other.lastUpdatedDate.copy();
    }

    @Override
    public CountryCriteria copy() {
        return new CountryCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCountryName() {
        return countryName;
    }

    public void setCountryName(StringFilter countryName) {
        this.countryName = countryName;
    }

    public StringFilter getCountryIsoCode() {
        return countryIsoCode;
    }

    public void setCountryIsoCode(StringFilter countryIsoCode) {
        this.countryIsoCode = countryIsoCode;
    }

    public StringFilter getCountryFlagUrl() {
        return countryFlagUrl;
    }

    public void setCountryFlagUrl(StringFilter countryFlagUrl) {
        this.countryFlagUrl = countryFlagUrl;
    }

    public StringFilter getCountryCallingCode() {
        return countryCallingCode;
    }

    public void setCountryCallingCode(StringFilter countryCallingCode) {
        this.countryCallingCode = countryCallingCode;
    }

    public IntegerFilter getCountryTelDigitLength() {
        return countryTelDigitLength;
    }

    public void setCountryTelDigitLength(IntegerFilter countryTelDigitLength) {
        this.countryTelDigitLength = countryTelDigitLength;
    }

    public ZonedDateTimeFilter getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(ZonedDateTimeFilter lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CountryCriteria that = (CountryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(countryName, that.countryName) &&
            Objects.equals(countryIsoCode, that.countryIsoCode) &&
            Objects.equals(countryFlagUrl, that.countryFlagUrl) &&
            Objects.equals(countryCallingCode, that.countryCallingCode) &&
            Objects.equals(countryTelDigitLength, that.countryTelDigitLength) &&
            Objects.equals(lastUpdatedDate, that.lastUpdatedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        countryName,
        countryIsoCode,
        countryFlagUrl,
        countryCallingCode,
        countryTelDigitLength,
        lastUpdatedDate
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CountryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (countryName != null ? "countryName=" + countryName + ", " : "") +
                (countryIsoCode != null ? "countryIsoCode=" + countryIsoCode + ", " : "") +
                (countryFlagUrl != null ? "countryFlagUrl=" + countryFlagUrl + ", " : "") +
                (countryCallingCode != null ? "countryCallingCode=" + countryCallingCode + ", " : "") +
                (countryTelDigitLength != null ? "countryTelDigitLength=" + countryTelDigitLength + ", " : "") +
                (lastUpdatedDate != null ? "lastUpdatedDate=" + lastUpdatedDate + ", " : "") +
            "}";
    }

}
