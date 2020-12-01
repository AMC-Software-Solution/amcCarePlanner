package com.amc.careplanner.service.dto;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;
import com.amc.careplanner.domain.enumeration.Title;
import com.amc.careplanner.domain.enumeration.Gender;
import com.amc.careplanner.domain.enumeration.EmployeeContractType;
import com.amc.careplanner.domain.enumeration.TravelMode;

/**
 * A DTO for the {@link com.amc.careplanner.domain.Employee} entity.
 */
public class EmployeeDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Title title;

    @NotNull
    @Size(min = 1, max = 25)
    private String firstName;

    @Size(max = 1)
    private String middleInitial;

    @NotNull
    @Size(min = 1, max = 25)
    private String lastName;

    @Size(max = 25)
    private String preferredName;

    @NotNull
    private Gender gender;

    @NotNull
    private String employeeCode;

    @NotNull
    private String email;

    private String nationalInsuranceNumber;

    @NotNull
    private EmployeeContractType employeeContractType;

    private Integer pinCode;

    @NotNull
    private TravelMode transportMode;

    @NotNull
    private String address;

    private String county;

    private String postCode;

    @NotNull
    private LocalDate dateOfBirth;

    @Lob
    private byte[] photo;

    private String photoContentType;
    private String photoUrl;

    private Integer acruedHolidayHours;

    private ZonedDateTime lastUpdatedDate;

    @NotNull
    private Long clientId;


    private Long userId;

    private String userLogin;

    private Long nationalityId;

    private String nationalityCountryName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleInitial() {
        return middleInitial;
    }

    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPreferredName() {
        return preferredName;
    }

    public void setPreferredName(String preferredName) {
        this.preferredName = preferredName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNationalInsuranceNumber() {
        return nationalInsuranceNumber;
    }

    public void setNationalInsuranceNumber(String nationalInsuranceNumber) {
        this.nationalInsuranceNumber = nationalInsuranceNumber;
    }

    public EmployeeContractType getEmployeeContractType() {
        return employeeContractType;
    }

    public void setEmployeeContractType(EmployeeContractType employeeContractType) {
        this.employeeContractType = employeeContractType;
    }

    public Integer getPinCode() {
        return pinCode;
    }

    public void setPinCode(Integer pinCode) {
        this.pinCode = pinCode;
    }

    public TravelMode getTransportMode() {
        return transportMode;
    }

    public void setTransportMode(TravelMode transportMode) {
        this.transportMode = transportMode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Integer getAcruedHolidayHours() {
        return acruedHolidayHours;
    }

    public void setAcruedHolidayHours(Integer acruedHolidayHours) {
        this.acruedHolidayHours = acruedHolidayHours;
    }

    public ZonedDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public Long getNationalityId() {
        return nationalityId;
    }

    public void setNationalityId(Long countryId) {
        this.nationalityId = countryId;
    }

    public String getNationalityCountryName() {
        return nationalityCountryName;
    }

    public void setNationalityCountryName(String countryCountryName) {
        this.nationalityCountryName = countryCountryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeDTO)) {
            return false;
        }

        return id != null && id.equals(((EmployeeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", middleInitial='" + getMiddleInitial() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", preferredName='" + getPreferredName() + "'" +
            ", gender='" + getGender() + "'" +
            ", employeeCode='" + getEmployeeCode() + "'" +
            ", email='" + getEmail() + "'" +
            ", nationalInsuranceNumber='" + getNationalInsuranceNumber() + "'" +
            ", employeeContractType='" + getEmployeeContractType() + "'" +
            ", pinCode=" + getPinCode() +
            ", transportMode='" + getTransportMode() + "'" +
            ", address='" + getAddress() + "'" +
            ", county='" + getCounty() + "'" +
            ", postCode='" + getPostCode() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoUrl='" + getPhotoUrl() + "'" +
            ", acruedHolidayHours=" + getAcruedHolidayHours() +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", clientId=" + getClientId() +
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            ", nationalityId=" + getNationalityId() +
            ", nationalityCountryName='" + getNationalityCountryName() + "'" +
            "}";
    }
}
