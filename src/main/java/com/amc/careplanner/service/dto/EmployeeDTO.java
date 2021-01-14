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
import com.amc.careplanner.domain.enumeration.EmployeeStatus;

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
    private String phone;

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

    private EmployeeStatus status;

    private String employeeBio;

    private Integer acruedHolidayHours;

    private ZonedDateTime createdDate;

    private ZonedDateTime lastUpdatedDate;

    @NotNull
    private Long clientId;

    private Boolean hasExtraData;


    private Long userId;

    private String userEmail;

    private Long designationId;

    private String designationDesignation;

    private Long nationalityId;

    private String nationalityCountryName;

    private Long branchId;

    private String branchName;
    
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public EmployeeStatus getStatus() {
        return status;
    }

    public void setStatus(EmployeeStatus status) {
        this.status = status;
    }

    public String getEmployeeBio() {
        return employeeBio;
    }

    public void setEmployeeBio(String employeeBio) {
        this.employeeBio = employeeBio;
    }

    public Integer getAcruedHolidayHours() {
        return acruedHolidayHours;
    }

    public void setAcruedHolidayHours(Integer acruedHolidayHours) {
        this.acruedHolidayHours = acruedHolidayHours;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
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

    public Boolean isHasExtraData() {
        return hasExtraData;
    }

    public void setHasExtraData(Boolean hasExtraData) {
        this.hasExtraData = hasExtraData;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Long getDesignationId() {
        return designationId;
    }

    public void setDesignationId(Long employeeDesignationId) {
        this.designationId = employeeDesignationId;
    }

    public String getDesignationDesignation() {
        return designationDesignation;
    }

    public void setDesignationDesignation(String employeeDesignationDesignation) {
        this.designationDesignation = employeeDesignationDesignation;
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

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
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
            ", phone='" + getPhone() + "'" +
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
            ", status='" + getStatus() + "'" +
            ", employeeBio='" + getEmployeeBio() + "'" +
            ", acruedHolidayHours=" + getAcruedHolidayHours() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", clientId=" + getClientId() +
            ", hasExtraData='" + isHasExtraData() + "'" +
            ", userId=" + getUserId() +
            ", userEmail='" + getUserEmail() + "'" +
            ", designationId=" + getDesignationId() +
            ", designationDesignation='" + getDesignationDesignation() + "'" +
            ", nationalityId=" + getNationalityId() +
            ", nationalityCountryName='" + getNationalityCountryName() + "'" +
            ", branchId=" + getBranchId() +
            ", branchName='" + getBranchName() + "'" +
            "}";
    }
}
