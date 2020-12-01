package com.amc.careplanner.service.dto;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;
import com.amc.careplanner.domain.enumeration.Title;
import com.amc.careplanner.domain.enumeration.SupportType;
import com.amc.careplanner.domain.enumeration.ServiceUserCategory;
import com.amc.careplanner.domain.enumeration.Vulnerability;
import com.amc.careplanner.domain.enumeration.ServicePriority;
import com.amc.careplanner.domain.enumeration.Source;
import com.amc.careplanner.domain.enumeration.ServiceUserStatus;

/**
 * A DTO for the {@link com.amc.careplanner.domain.ServiceUser} entity.
 */
public class ServiceUserDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Title title;

    @NotNull
    @Size(min = 1, max = 25)
    private String firstName;

    @Size(max = 100)
    private String middleName;

    @NotNull
    @Size(min = 1, max = 25)
    private String lastName;

    @Size(max = 25)
    private String preferredName;

    private String email;

    @NotNull
    private String serviceUserCode;

    @NotNull
    private LocalDate dateOfBirth;

    private ZonedDateTime lastVisitDate;

    @NotNull
    private ZonedDateTime startDate;

    @NotNull
    private SupportType supportType;

    @NotNull
    private ServiceUserCategory serviceUserCategory;

    @NotNull
    private Vulnerability vulnerability;

    @NotNull
    private ServicePriority servicePriority;

    @NotNull
    private Source source;

    @NotNull
    private ServiceUserStatus status;

    @Size(max = 25)
    private String firstLanguage;

    private Boolean interpreterRequired;

    private ZonedDateTime activatedDate;

    @Lob
    private byte[] profilePhoto;

    private String profilePhotoContentType;
    private String profilePhotoUrl;

    private String lastRecordedHeight;

    private String lastRecordedWeight;

    private Boolean hasMedicalCondition;

    private String medicalConditionSummary;

    private ZonedDateTime lastUpdatedDate;

    @NotNull
    private Long clientId;


    private Long userId;

    private String userLogin;

    private Long branchId;

    private String branchName;

    private Long registeredById;

    private String registeredByEmployeeCode;

    private Long activatedById;

    private String activatedByEmployeeCode;
    
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

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getServiceUserCode() {
        return serviceUserCode;
    }

    public void setServiceUserCode(String serviceUserCode) {
        this.serviceUserCode = serviceUserCode;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public ZonedDateTime getLastVisitDate() {
        return lastVisitDate;
    }

    public void setLastVisitDate(ZonedDateTime lastVisitDate) {
        this.lastVisitDate = lastVisitDate;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public SupportType getSupportType() {
        return supportType;
    }

    public void setSupportType(SupportType supportType) {
        this.supportType = supportType;
    }

    public ServiceUserCategory getServiceUserCategory() {
        return serviceUserCategory;
    }

    public void setServiceUserCategory(ServiceUserCategory serviceUserCategory) {
        this.serviceUserCategory = serviceUserCategory;
    }

    public Vulnerability getVulnerability() {
        return vulnerability;
    }

    public void setVulnerability(Vulnerability vulnerability) {
        this.vulnerability = vulnerability;
    }

    public ServicePriority getServicePriority() {
        return servicePriority;
    }

    public void setServicePriority(ServicePriority servicePriority) {
        this.servicePriority = servicePriority;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public ServiceUserStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceUserStatus status) {
        this.status = status;
    }

    public String getFirstLanguage() {
        return firstLanguage;
    }

    public void setFirstLanguage(String firstLanguage) {
        this.firstLanguage = firstLanguage;
    }

    public Boolean isInterpreterRequired() {
        return interpreterRequired;
    }

    public void setInterpreterRequired(Boolean interpreterRequired) {
        this.interpreterRequired = interpreterRequired;
    }

    public ZonedDateTime getActivatedDate() {
        return activatedDate;
    }

    public void setActivatedDate(ZonedDateTime activatedDate) {
        this.activatedDate = activatedDate;
    }

    public byte[] getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(byte[] profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getProfilePhotoContentType() {
        return profilePhotoContentType;
    }

    public void setProfilePhotoContentType(String profilePhotoContentType) {
        this.profilePhotoContentType = profilePhotoContentType;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public String getLastRecordedHeight() {
        return lastRecordedHeight;
    }

    public void setLastRecordedHeight(String lastRecordedHeight) {
        this.lastRecordedHeight = lastRecordedHeight;
    }

    public String getLastRecordedWeight() {
        return lastRecordedWeight;
    }

    public void setLastRecordedWeight(String lastRecordedWeight) {
        this.lastRecordedWeight = lastRecordedWeight;
    }

    public Boolean isHasMedicalCondition() {
        return hasMedicalCondition;
    }

    public void setHasMedicalCondition(Boolean hasMedicalCondition) {
        this.hasMedicalCondition = hasMedicalCondition;
    }

    public String getMedicalConditionSummary() {
        return medicalConditionSummary;
    }

    public void setMedicalConditionSummary(String medicalConditionSummary) {
        this.medicalConditionSummary = medicalConditionSummary;
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

    public Long getRegisteredById() {
        return registeredById;
    }

    public void setRegisteredById(Long employeeId) {
        this.registeredById = employeeId;
    }

    public String getRegisteredByEmployeeCode() {
        return registeredByEmployeeCode;
    }

    public void setRegisteredByEmployeeCode(String employeeEmployeeCode) {
        this.registeredByEmployeeCode = employeeEmployeeCode;
    }

    public Long getActivatedById() {
        return activatedById;
    }

    public void setActivatedById(Long employeeId) {
        this.activatedById = employeeId;
    }

    public String getActivatedByEmployeeCode() {
        return activatedByEmployeeCode;
    }

    public void setActivatedByEmployeeCode(String employeeEmployeeCode) {
        this.activatedByEmployeeCode = employeeEmployeeCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceUserDTO)) {
            return false;
        }

        return id != null && id.equals(((ServiceUserDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceUserDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", preferredName='" + getPreferredName() + "'" +
            ", email='" + getEmail() + "'" +
            ", serviceUserCode='" + getServiceUserCode() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", lastVisitDate='" + getLastVisitDate() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", supportType='" + getSupportType() + "'" +
            ", serviceUserCategory='" + getServiceUserCategory() + "'" +
            ", vulnerability='" + getVulnerability() + "'" +
            ", servicePriority='" + getServicePriority() + "'" +
            ", source='" + getSource() + "'" +
            ", status='" + getStatus() + "'" +
            ", firstLanguage='" + getFirstLanguage() + "'" +
            ", interpreterRequired='" + isInterpreterRequired() + "'" +
            ", activatedDate='" + getActivatedDate() + "'" +
            ", profilePhoto='" + getProfilePhoto() + "'" +
            ", profilePhotoUrl='" + getProfilePhotoUrl() + "'" +
            ", lastRecordedHeight='" + getLastRecordedHeight() + "'" +
            ", lastRecordedWeight='" + getLastRecordedWeight() + "'" +
            ", hasMedicalCondition='" + isHasMedicalCondition() + "'" +
            ", medicalConditionSummary='" + getMedicalConditionSummary() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", clientId=" + getClientId() +
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            ", branchId=" + getBranchId() +
            ", branchName='" + getBranchName() + "'" +
            ", registeredById=" + getRegisteredById() +
            ", registeredByEmployeeCode='" + getRegisteredByEmployeeCode() + "'" +
            ", activatedById=" + getActivatedById() +
            ", activatedByEmployeeCode='" + getActivatedByEmployeeCode() + "'" +
            "}";
    }
}
