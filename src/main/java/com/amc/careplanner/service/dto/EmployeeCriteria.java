package com.amc.careplanner.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.amc.careplanner.domain.enumeration.Title;
import com.amc.careplanner.domain.enumeration.Gender;
import com.amc.careplanner.domain.enumeration.EmployeeContractType;
import com.amc.careplanner.domain.enumeration.TravelMode;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.amc.careplanner.domain.Employee} entity. This class is used
 * in {@link com.amc.careplanner.web.rest.EmployeeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employees?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EmployeeCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Title
     */
    public static class TitleFilter extends Filter<Title> {

        public TitleFilter() {
        }

        public TitleFilter(TitleFilter filter) {
            super(filter);
        }

        @Override
        public TitleFilter copy() {
            return new TitleFilter(this);
        }

    }
    /**
     * Class for filtering Gender
     */
    public static class GenderFilter extends Filter<Gender> {

        public GenderFilter() {
        }

        public GenderFilter(GenderFilter filter) {
            super(filter);
        }

        @Override
        public GenderFilter copy() {
            return new GenderFilter(this);
        }

    }
    /**
     * Class for filtering EmployeeContractType
     */
    public static class EmployeeContractTypeFilter extends Filter<EmployeeContractType> {

        public EmployeeContractTypeFilter() {
        }

        public EmployeeContractTypeFilter(EmployeeContractTypeFilter filter) {
            super(filter);
        }

        @Override
        public EmployeeContractTypeFilter copy() {
            return new EmployeeContractTypeFilter(this);
        }

    }
    /**
     * Class for filtering TravelMode
     */
    public static class TravelModeFilter extends Filter<TravelMode> {

        public TravelModeFilter() {
        }

        public TravelModeFilter(TravelModeFilter filter) {
            super(filter);
        }

        @Override
        public TravelModeFilter copy() {
            return new TravelModeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private TitleFilter title;

    private StringFilter firstName;

    private StringFilter middleInitial;

    private StringFilter lastName;

    private StringFilter preferredName;

    private GenderFilter gender;

    private StringFilter employeeCode;

    private StringFilter email;

    private StringFilter nationalInsuranceNumber;

    private EmployeeContractTypeFilter employeeContractType;

    private IntegerFilter pinCode;

    private TravelModeFilter transportMode;

    private StringFilter address;

    private StringFilter county;

    private StringFilter postCode;

    private LocalDateFilter dateOfBirth;

    private StringFilter photoUrl;

    private IntegerFilter acruedHolidayHours;

    private ZonedDateTimeFilter lastUpdatedDate;

    private LongFilter clientId;

    private LongFilter userId;

    private LongFilter nationalityId;

    public EmployeeCriteria() {
    }

    public EmployeeCriteria(EmployeeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.firstName = other.firstName == null ? null : other.firstName.copy();
        this.middleInitial = other.middleInitial == null ? null : other.middleInitial.copy();
        this.lastName = other.lastName == null ? null : other.lastName.copy();
        this.preferredName = other.preferredName == null ? null : other.preferredName.copy();
        this.gender = other.gender == null ? null : other.gender.copy();
        this.employeeCode = other.employeeCode == null ? null : other.employeeCode.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.nationalInsuranceNumber = other.nationalInsuranceNumber == null ? null : other.nationalInsuranceNumber.copy();
        this.employeeContractType = other.employeeContractType == null ? null : other.employeeContractType.copy();
        this.pinCode = other.pinCode == null ? null : other.pinCode.copy();
        this.transportMode = other.transportMode == null ? null : other.transportMode.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.county = other.county == null ? null : other.county.copy();
        this.postCode = other.postCode == null ? null : other.postCode.copy();
        this.dateOfBirth = other.dateOfBirth == null ? null : other.dateOfBirth.copy();
        this.photoUrl = other.photoUrl == null ? null : other.photoUrl.copy();
        this.acruedHolidayHours = other.acruedHolidayHours == null ? null : other.acruedHolidayHours.copy();
        this.lastUpdatedDate = other.lastUpdatedDate == null ? null : other.lastUpdatedDate.copy();
        this.clientId = other.clientId == null ? null : other.clientId.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.nationalityId = other.nationalityId == null ? null : other.nationalityId.copy();
    }

    @Override
    public EmployeeCriteria copy() {
        return new EmployeeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public TitleFilter getTitle() {
        return title;
    }

    public void setTitle(TitleFilter title) {
        this.title = title;
    }

    public StringFilter getFirstName() {
        return firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public StringFilter getMiddleInitial() {
        return middleInitial;
    }

    public void setMiddleInitial(StringFilter middleInitial) {
        this.middleInitial = middleInitial;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
    }

    public StringFilter getPreferredName() {
        return preferredName;
    }

    public void setPreferredName(StringFilter preferredName) {
        this.preferredName = preferredName;
    }

    public GenderFilter getGender() {
        return gender;
    }

    public void setGender(GenderFilter gender) {
        this.gender = gender;
    }

    public StringFilter getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(StringFilter employeeCode) {
        this.employeeCode = employeeCode;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getNationalInsuranceNumber() {
        return nationalInsuranceNumber;
    }

    public void setNationalInsuranceNumber(StringFilter nationalInsuranceNumber) {
        this.nationalInsuranceNumber = nationalInsuranceNumber;
    }

    public EmployeeContractTypeFilter getEmployeeContractType() {
        return employeeContractType;
    }

    public void setEmployeeContractType(EmployeeContractTypeFilter employeeContractType) {
        this.employeeContractType = employeeContractType;
    }

    public IntegerFilter getPinCode() {
        return pinCode;
    }

    public void setPinCode(IntegerFilter pinCode) {
        this.pinCode = pinCode;
    }

    public TravelModeFilter getTransportMode() {
        return transportMode;
    }

    public void setTransportMode(TravelModeFilter transportMode) {
        this.transportMode = transportMode;
    }

    public StringFilter getAddress() {
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }

    public StringFilter getCounty() {
        return county;
    }

    public void setCounty(StringFilter county) {
        this.county = county;
    }

    public StringFilter getPostCode() {
        return postCode;
    }

    public void setPostCode(StringFilter postCode) {
        this.postCode = postCode;
    }

    public LocalDateFilter getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDateFilter dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public StringFilter getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(StringFilter photoUrl) {
        this.photoUrl = photoUrl;
    }

    public IntegerFilter getAcruedHolidayHours() {
        return acruedHolidayHours;
    }

    public void setAcruedHolidayHours(IntegerFilter acruedHolidayHours) {
        this.acruedHolidayHours = acruedHolidayHours;
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

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getNationalityId() {
        return nationalityId;
    }

    public void setNationalityId(LongFilter nationalityId) {
        this.nationalityId = nationalityId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EmployeeCriteria that = (EmployeeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(middleInitial, that.middleInitial) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(preferredName, that.preferredName) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(employeeCode, that.employeeCode) &&
            Objects.equals(email, that.email) &&
            Objects.equals(nationalInsuranceNumber, that.nationalInsuranceNumber) &&
            Objects.equals(employeeContractType, that.employeeContractType) &&
            Objects.equals(pinCode, that.pinCode) &&
            Objects.equals(transportMode, that.transportMode) &&
            Objects.equals(address, that.address) &&
            Objects.equals(county, that.county) &&
            Objects.equals(postCode, that.postCode) &&
            Objects.equals(dateOfBirth, that.dateOfBirth) &&
            Objects.equals(photoUrl, that.photoUrl) &&
            Objects.equals(acruedHolidayHours, that.acruedHolidayHours) &&
            Objects.equals(lastUpdatedDate, that.lastUpdatedDate) &&
            Objects.equals(clientId, that.clientId) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(nationalityId, that.nationalityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        title,
        firstName,
        middleInitial,
        lastName,
        preferredName,
        gender,
        employeeCode,
        email,
        nationalInsuranceNumber,
        employeeContractType,
        pinCode,
        transportMode,
        address,
        county,
        postCode,
        dateOfBirth,
        photoUrl,
        acruedHolidayHours,
        lastUpdatedDate,
        clientId,
        userId,
        nationalityId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (firstName != null ? "firstName=" + firstName + ", " : "") +
                (middleInitial != null ? "middleInitial=" + middleInitial + ", " : "") +
                (lastName != null ? "lastName=" + lastName + ", " : "") +
                (preferredName != null ? "preferredName=" + preferredName + ", " : "") +
                (gender != null ? "gender=" + gender + ", " : "") +
                (employeeCode != null ? "employeeCode=" + employeeCode + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (nationalInsuranceNumber != null ? "nationalInsuranceNumber=" + nationalInsuranceNumber + ", " : "") +
                (employeeContractType != null ? "employeeContractType=" + employeeContractType + ", " : "") +
                (pinCode != null ? "pinCode=" + pinCode + ", " : "") +
                (transportMode != null ? "transportMode=" + transportMode + ", " : "") +
                (address != null ? "address=" + address + ", " : "") +
                (county != null ? "county=" + county + ", " : "") +
                (postCode != null ? "postCode=" + postCode + ", " : "") +
                (dateOfBirth != null ? "dateOfBirth=" + dateOfBirth + ", " : "") +
                (photoUrl != null ? "photoUrl=" + photoUrl + ", " : "") +
                (acruedHolidayHours != null ? "acruedHolidayHours=" + acruedHolidayHours + ", " : "") +
                (lastUpdatedDate != null ? "lastUpdatedDate=" + lastUpdatedDate + ", " : "") +
                (clientId != null ? "clientId=" + clientId + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (nationalityId != null ? "nationalityId=" + nationalityId + ", " : "") +
            "}";
    }

}
