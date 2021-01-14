package com.amc.careplanner.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.amc.careplanner.domain.enumeration.Title;
import com.amc.careplanner.domain.enumeration.SupportType;
import com.amc.careplanner.domain.enumeration.ServiceUserCategory;
import com.amc.careplanner.domain.enumeration.Vulnerability;
import com.amc.careplanner.domain.enumeration.ServicePriority;
import com.amc.careplanner.domain.enumeration.Source;
import com.amc.careplanner.domain.enumeration.ServiceUserStatus;
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
 * Criteria class for the {@link com.amc.careplanner.domain.ServiceUser} entity. This class is used
 * in {@link com.amc.careplanner.web.rest.ServiceUserResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /service-users?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ServiceUserCriteria implements Serializable, Criteria {
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
     * Class for filtering SupportType
     */
    public static class SupportTypeFilter extends Filter<SupportType> {

        public SupportTypeFilter() {
        }

        public SupportTypeFilter(SupportTypeFilter filter) {
            super(filter);
        }

        @Override
        public SupportTypeFilter copy() {
            return new SupportTypeFilter(this);
        }

    }
    /**
     * Class for filtering ServiceUserCategory
     */
    public static class ServiceUserCategoryFilter extends Filter<ServiceUserCategory> {

        public ServiceUserCategoryFilter() {
        }

        public ServiceUserCategoryFilter(ServiceUserCategoryFilter filter) {
            super(filter);
        }

        @Override
        public ServiceUserCategoryFilter copy() {
            return new ServiceUserCategoryFilter(this);
        }

    }
    /**
     * Class for filtering Vulnerability
     */
    public static class VulnerabilityFilter extends Filter<Vulnerability> {

        public VulnerabilityFilter() {
        }

        public VulnerabilityFilter(VulnerabilityFilter filter) {
            super(filter);
        }

        @Override
        public VulnerabilityFilter copy() {
            return new VulnerabilityFilter(this);
        }

    }
    /**
     * Class for filtering ServicePriority
     */
    public static class ServicePriorityFilter extends Filter<ServicePriority> {

        public ServicePriorityFilter() {
        }

        public ServicePriorityFilter(ServicePriorityFilter filter) {
            super(filter);
        }

        @Override
        public ServicePriorityFilter copy() {
            return new ServicePriorityFilter(this);
        }

    }
    /**
     * Class for filtering Source
     */
    public static class SourceFilter extends Filter<Source> {

        public SourceFilter() {
        }

        public SourceFilter(SourceFilter filter) {
            super(filter);
        }

        @Override
        public SourceFilter copy() {
            return new SourceFilter(this);
        }

    }
    /**
     * Class for filtering ServiceUserStatus
     */
    public static class ServiceUserStatusFilter extends Filter<ServiceUserStatus> {

        public ServiceUserStatusFilter() {
        }

        public ServiceUserStatusFilter(ServiceUserStatusFilter filter) {
            super(filter);
        }

        @Override
        public ServiceUserStatusFilter copy() {
            return new ServiceUserStatusFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private TitleFilter title;

    private StringFilter firstName;

    private StringFilter middleName;

    private StringFilter lastName;

    private StringFilter preferredName;

    private StringFilter email;

    private StringFilter serviceUserCode;

    private LocalDateFilter dateOfBirth;

    private ZonedDateTimeFilter lastVisitDate;

    private ZonedDateTimeFilter startDate;

    private SupportTypeFilter supportType;

    private ServiceUserCategoryFilter serviceUserCategory;

    private VulnerabilityFilter vulnerability;

    private ServicePriorityFilter servicePriority;

    private SourceFilter source;

    private ServiceUserStatusFilter status;

    private StringFilter firstLanguage;

    private BooleanFilter interpreterRequired;

    private ZonedDateTimeFilter activatedDate;

    private StringFilter profilePhotoUrl;

    private StringFilter lastRecordedHeight;

    private StringFilter lastRecordedWeight;

    private BooleanFilter hasMedicalCondition;

    private StringFilter medicalConditionSummary;

    private ZonedDateTimeFilter createdDate;

    private ZonedDateTimeFilter lastUpdatedDate;

    private LongFilter clientId;

    private BooleanFilter hasExtraData;

    private LongFilter userId;

    private LongFilter branchId;

    private LongFilter registeredById;

    private LongFilter activatedById;

    public ServiceUserCriteria() {
    }

    public ServiceUserCriteria(ServiceUserCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.firstName = other.firstName == null ? null : other.firstName.copy();
        this.middleName = other.middleName == null ? null : other.middleName.copy();
        this.lastName = other.lastName == null ? null : other.lastName.copy();
        this.preferredName = other.preferredName == null ? null : other.preferredName.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.serviceUserCode = other.serviceUserCode == null ? null : other.serviceUserCode.copy();
        this.dateOfBirth = other.dateOfBirth == null ? null : other.dateOfBirth.copy();
        this.lastVisitDate = other.lastVisitDate == null ? null : other.lastVisitDate.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.supportType = other.supportType == null ? null : other.supportType.copy();
        this.serviceUserCategory = other.serviceUserCategory == null ? null : other.serviceUserCategory.copy();
        this.vulnerability = other.vulnerability == null ? null : other.vulnerability.copy();
        this.servicePriority = other.servicePriority == null ? null : other.servicePriority.copy();
        this.source = other.source == null ? null : other.source.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.firstLanguage = other.firstLanguage == null ? null : other.firstLanguage.copy();
        this.interpreterRequired = other.interpreterRequired == null ? null : other.interpreterRequired.copy();
        this.activatedDate = other.activatedDate == null ? null : other.activatedDate.copy();
        this.profilePhotoUrl = other.profilePhotoUrl == null ? null : other.profilePhotoUrl.copy();
        this.lastRecordedHeight = other.lastRecordedHeight == null ? null : other.lastRecordedHeight.copy();
        this.lastRecordedWeight = other.lastRecordedWeight == null ? null : other.lastRecordedWeight.copy();
        this.hasMedicalCondition = other.hasMedicalCondition == null ? null : other.hasMedicalCondition.copy();
        this.medicalConditionSummary = other.medicalConditionSummary == null ? null : other.medicalConditionSummary.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.lastUpdatedDate = other.lastUpdatedDate == null ? null : other.lastUpdatedDate.copy();
        this.clientId = other.clientId == null ? null : other.clientId.copy();
        this.hasExtraData = other.hasExtraData == null ? null : other.hasExtraData.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.branchId = other.branchId == null ? null : other.branchId.copy();
        this.registeredById = other.registeredById == null ? null : other.registeredById.copy();
        this.activatedById = other.activatedById == null ? null : other.activatedById.copy();
    }

    @Override
    public ServiceUserCriteria copy() {
        return new ServiceUserCriteria(this);
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

    public StringFilter getMiddleName() {
        return middleName;
    }

    public void setMiddleName(StringFilter middleName) {
        this.middleName = middleName;
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

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getServiceUserCode() {
        return serviceUserCode;
    }

    public void setServiceUserCode(StringFilter serviceUserCode) {
        this.serviceUserCode = serviceUserCode;
    }

    public LocalDateFilter getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDateFilter dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public ZonedDateTimeFilter getLastVisitDate() {
        return lastVisitDate;
    }

    public void setLastVisitDate(ZonedDateTimeFilter lastVisitDate) {
        this.lastVisitDate = lastVisitDate;
    }

    public ZonedDateTimeFilter getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTimeFilter startDate) {
        this.startDate = startDate;
    }

    public SupportTypeFilter getSupportType() {
        return supportType;
    }

    public void setSupportType(SupportTypeFilter supportType) {
        this.supportType = supportType;
    }

    public ServiceUserCategoryFilter getServiceUserCategory() {
        return serviceUserCategory;
    }

    public void setServiceUserCategory(ServiceUserCategoryFilter serviceUserCategory) {
        this.serviceUserCategory = serviceUserCategory;
    }

    public VulnerabilityFilter getVulnerability() {
        return vulnerability;
    }

    public void setVulnerability(VulnerabilityFilter vulnerability) {
        this.vulnerability = vulnerability;
    }

    public ServicePriorityFilter getServicePriority() {
        return servicePriority;
    }

    public void setServicePriority(ServicePriorityFilter servicePriority) {
        this.servicePriority = servicePriority;
    }

    public SourceFilter getSource() {
        return source;
    }

    public void setSource(SourceFilter source) {
        this.source = source;
    }

    public ServiceUserStatusFilter getStatus() {
        return status;
    }

    public void setStatus(ServiceUserStatusFilter status) {
        this.status = status;
    }

    public StringFilter getFirstLanguage() {
        return firstLanguage;
    }

    public void setFirstLanguage(StringFilter firstLanguage) {
        this.firstLanguage = firstLanguage;
    }

    public BooleanFilter getInterpreterRequired() {
        return interpreterRequired;
    }

    public void setInterpreterRequired(BooleanFilter interpreterRequired) {
        this.interpreterRequired = interpreterRequired;
    }

    public ZonedDateTimeFilter getActivatedDate() {
        return activatedDate;
    }

    public void setActivatedDate(ZonedDateTimeFilter activatedDate) {
        this.activatedDate = activatedDate;
    }

    public StringFilter getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public void setProfilePhotoUrl(StringFilter profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public StringFilter getLastRecordedHeight() {
        return lastRecordedHeight;
    }

    public void setLastRecordedHeight(StringFilter lastRecordedHeight) {
        this.lastRecordedHeight = lastRecordedHeight;
    }

    public StringFilter getLastRecordedWeight() {
        return lastRecordedWeight;
    }

    public void setLastRecordedWeight(StringFilter lastRecordedWeight) {
        this.lastRecordedWeight = lastRecordedWeight;
    }

    public BooleanFilter getHasMedicalCondition() {
        return hasMedicalCondition;
    }

    public void setHasMedicalCondition(BooleanFilter hasMedicalCondition) {
        this.hasMedicalCondition = hasMedicalCondition;
    }

    public StringFilter getMedicalConditionSummary() {
        return medicalConditionSummary;
    }

    public void setMedicalConditionSummary(StringFilter medicalConditionSummary) {
        this.medicalConditionSummary = medicalConditionSummary;
    }

    public ZonedDateTimeFilter getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTimeFilter createdDate) {
        this.createdDate = createdDate;
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

    public BooleanFilter getHasExtraData() {
        return hasExtraData;
    }

    public void setHasExtraData(BooleanFilter hasExtraData) {
        this.hasExtraData = hasExtraData;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getBranchId() {
        return branchId;
    }

    public void setBranchId(LongFilter branchId) {
        this.branchId = branchId;
    }

    public LongFilter getRegisteredById() {
        return registeredById;
    }

    public void setRegisteredById(LongFilter registeredById) {
        this.registeredById = registeredById;
    }

    public LongFilter getActivatedById() {
        return activatedById;
    }

    public void setActivatedById(LongFilter activatedById) {
        this.activatedById = activatedById;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ServiceUserCriteria that = (ServiceUserCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(middleName, that.middleName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(preferredName, that.preferredName) &&
            Objects.equals(email, that.email) &&
            Objects.equals(serviceUserCode, that.serviceUserCode) &&
            Objects.equals(dateOfBirth, that.dateOfBirth) &&
            Objects.equals(lastVisitDate, that.lastVisitDate) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(supportType, that.supportType) &&
            Objects.equals(serviceUserCategory, that.serviceUserCategory) &&
            Objects.equals(vulnerability, that.vulnerability) &&
            Objects.equals(servicePriority, that.servicePriority) &&
            Objects.equals(source, that.source) &&
            Objects.equals(status, that.status) &&
            Objects.equals(firstLanguage, that.firstLanguage) &&
            Objects.equals(interpreterRequired, that.interpreterRequired) &&
            Objects.equals(activatedDate, that.activatedDate) &&
            Objects.equals(profilePhotoUrl, that.profilePhotoUrl) &&
            Objects.equals(lastRecordedHeight, that.lastRecordedHeight) &&
            Objects.equals(lastRecordedWeight, that.lastRecordedWeight) &&
            Objects.equals(hasMedicalCondition, that.hasMedicalCondition) &&
            Objects.equals(medicalConditionSummary, that.medicalConditionSummary) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastUpdatedDate, that.lastUpdatedDate) &&
            Objects.equals(clientId, that.clientId) &&
            Objects.equals(hasExtraData, that.hasExtraData) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(branchId, that.branchId) &&
            Objects.equals(registeredById, that.registeredById) &&
            Objects.equals(activatedById, that.activatedById);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        title,
        firstName,
        middleName,
        lastName,
        preferredName,
        email,
        serviceUserCode,
        dateOfBirth,
        lastVisitDate,
        startDate,
        supportType,
        serviceUserCategory,
        vulnerability,
        servicePriority,
        source,
        status,
        firstLanguage,
        interpreterRequired,
        activatedDate,
        profilePhotoUrl,
        lastRecordedHeight,
        lastRecordedWeight,
        hasMedicalCondition,
        medicalConditionSummary,
        createdDate,
        lastUpdatedDate,
        clientId,
        hasExtraData,
        userId,
        branchId,
        registeredById,
        activatedById
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceUserCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (firstName != null ? "firstName=" + firstName + ", " : "") +
                (middleName != null ? "middleName=" + middleName + ", " : "") +
                (lastName != null ? "lastName=" + lastName + ", " : "") +
                (preferredName != null ? "preferredName=" + preferredName + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (serviceUserCode != null ? "serviceUserCode=" + serviceUserCode + ", " : "") +
                (dateOfBirth != null ? "dateOfBirth=" + dateOfBirth + ", " : "") +
                (lastVisitDate != null ? "lastVisitDate=" + lastVisitDate + ", " : "") +
                (startDate != null ? "startDate=" + startDate + ", " : "") +
                (supportType != null ? "supportType=" + supportType + ", " : "") +
                (serviceUserCategory != null ? "serviceUserCategory=" + serviceUserCategory + ", " : "") +
                (vulnerability != null ? "vulnerability=" + vulnerability + ", " : "") +
                (servicePriority != null ? "servicePriority=" + servicePriority + ", " : "") +
                (source != null ? "source=" + source + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (firstLanguage != null ? "firstLanguage=" + firstLanguage + ", " : "") +
                (interpreterRequired != null ? "interpreterRequired=" + interpreterRequired + ", " : "") +
                (activatedDate != null ? "activatedDate=" + activatedDate + ", " : "") +
                (profilePhotoUrl != null ? "profilePhotoUrl=" + profilePhotoUrl + ", " : "") +
                (lastRecordedHeight != null ? "lastRecordedHeight=" + lastRecordedHeight + ", " : "") +
                (lastRecordedWeight != null ? "lastRecordedWeight=" + lastRecordedWeight + ", " : "") +
                (hasMedicalCondition != null ? "hasMedicalCondition=" + hasMedicalCondition + ", " : "") +
                (medicalConditionSummary != null ? "medicalConditionSummary=" + medicalConditionSummary + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastUpdatedDate != null ? "lastUpdatedDate=" + lastUpdatedDate + ", " : "") +
                (clientId != null ? "clientId=" + clientId + ", " : "") +
                (hasExtraData != null ? "hasExtraData=" + hasExtraData + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (branchId != null ? "branchId=" + branchId + ", " : "") +
                (registeredById != null ? "registeredById=" + registeredById + ", " : "") +
                (activatedById != null ? "activatedById=" + activatedById + ", " : "") +
            "}";
    }

}
