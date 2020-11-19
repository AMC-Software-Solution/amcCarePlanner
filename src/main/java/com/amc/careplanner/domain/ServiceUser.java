package com.amc.careplanner.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

import com.amc.careplanner.domain.enumeration.Title;

import com.amc.careplanner.domain.enumeration.SupportType;

import com.amc.careplanner.domain.enumeration.ServiceUserCategory;

import com.amc.careplanner.domain.enumeration.Vulnerability;

import com.amc.careplanner.domain.enumeration.ServicePriority;

import com.amc.careplanner.domain.enumeration.Source;

import com.amc.careplanner.domain.enumeration.ServiceUserStatus;

/**
 * A ServiceUser.
 */
@Entity
@Table(name = "service_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ServiceUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "title", nullable = false)
    private Title title;

    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "first_name", length = 25, nullable = false)
    private String firstName;

    @Size(max = 100)
    @Column(name = "middle_name", length = 100)
    private String middleName;

    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "last_name", length = 25, nullable = false)
    private String lastName;

    @Size(max = 25)
    @Column(name = "preferred_name", length = 25)
    private String preferredName;

    @NotNull
    @Column(name = "service_user_code", nullable = false, unique = true)
    private String serviceUserCode;

    @NotNull
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "last_visit_date")
    private ZonedDateTime lastVisitDate;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private ZonedDateTime startDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "support_type", nullable = false)
    private SupportType supportType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "service_user_category", nullable = false)
    private ServiceUserCategory serviceUserCategory;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "vulnerability", nullable = false)
    private Vulnerability vulnerability;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "service_priority", nullable = false)
    private ServicePriority servicePriority;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "source", nullable = false)
    private Source source;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ServiceUserStatus status;

    @Size(max = 25)
    @Column(name = "first_language", length = 25)
    private String firstLanguage;

    @Column(name = "interpreter_required")
    private Boolean interpreterRequired;

    @Column(name = "activated_date")
    private ZonedDateTime activatedDate;

    @Lob
    @Column(name = "profile_photo")
    private byte[] profilePhoto;

    @Column(name = "profile_photo_content_type")
    private String profilePhotoContentType;

    @Column(name = "profile_photo_url")
    private String profilePhotoUrl;

    @Column(name = "last_recorded_height")
    private String lastRecordedHeight;

    @Column(name = "last_recorded_weight")
    private String lastRecordedWeight;

    @Column(name = "has_medical_condition")
    private Boolean hasMedicalCondition;

    @Column(name = "medical_condition_summary")
    private String medicalConditionSummary;

    @Column(name = "last_updated_date")
    private ZonedDateTime lastUpdatedDate;

    @NotNull
    @Column(name = "tenant_id", nullable = false)
    private Long tenantId;

    @ManyToOne
    @JsonIgnoreProperties(value = "serviceUsers", allowSetters = true)
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = "serviceUsers", allowSetters = true)
    private Branch branch;

    @ManyToOne
    @JsonIgnoreProperties(value = "serviceUsers", allowSetters = true)
    private Employee registeredBy;

    @ManyToOne
    @JsonIgnoreProperties(value = "serviceUsers", allowSetters = true)
    private Employee activatedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Title getTitle() {
        return title;
    }

    public ServiceUser title(Title title) {
        this.title = title;
        return this;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public ServiceUser firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public ServiceUser middleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public ServiceUser lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPreferredName() {
        return preferredName;
    }

    public ServiceUser preferredName(String preferredName) {
        this.preferredName = preferredName;
        return this;
    }

    public void setPreferredName(String preferredName) {
        this.preferredName = preferredName;
    }

    public String getServiceUserCode() {
        return serviceUserCode;
    }

    public ServiceUser serviceUserCode(String serviceUserCode) {
        this.serviceUserCode = serviceUserCode;
        return this;
    }

    public void setServiceUserCode(String serviceUserCode) {
        this.serviceUserCode = serviceUserCode;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public ServiceUser dateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public ZonedDateTime getLastVisitDate() {
        return lastVisitDate;
    }

    public ServiceUser lastVisitDate(ZonedDateTime lastVisitDate) {
        this.lastVisitDate = lastVisitDate;
        return this;
    }

    public void setLastVisitDate(ZonedDateTime lastVisitDate) {
        this.lastVisitDate = lastVisitDate;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public ServiceUser startDate(ZonedDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public SupportType getSupportType() {
        return supportType;
    }

    public ServiceUser supportType(SupportType supportType) {
        this.supportType = supportType;
        return this;
    }

    public void setSupportType(SupportType supportType) {
        this.supportType = supportType;
    }

    public ServiceUserCategory getServiceUserCategory() {
        return serviceUserCategory;
    }

    public ServiceUser serviceUserCategory(ServiceUserCategory serviceUserCategory) {
        this.serviceUserCategory = serviceUserCategory;
        return this;
    }

    public void setServiceUserCategory(ServiceUserCategory serviceUserCategory) {
        this.serviceUserCategory = serviceUserCategory;
    }

    public Vulnerability getVulnerability() {
        return vulnerability;
    }

    public ServiceUser vulnerability(Vulnerability vulnerability) {
        this.vulnerability = vulnerability;
        return this;
    }

    public void setVulnerability(Vulnerability vulnerability) {
        this.vulnerability = vulnerability;
    }

    public ServicePriority getServicePriority() {
        return servicePriority;
    }

    public ServiceUser servicePriority(ServicePriority servicePriority) {
        this.servicePriority = servicePriority;
        return this;
    }

    public void setServicePriority(ServicePriority servicePriority) {
        this.servicePriority = servicePriority;
    }

    public Source getSource() {
        return source;
    }

    public ServiceUser source(Source source) {
        this.source = source;
        return this;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public ServiceUserStatus getStatus() {
        return status;
    }

    public ServiceUser status(ServiceUserStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ServiceUserStatus status) {
        this.status = status;
    }

    public String getFirstLanguage() {
        return firstLanguage;
    }

    public ServiceUser firstLanguage(String firstLanguage) {
        this.firstLanguage = firstLanguage;
        return this;
    }

    public void setFirstLanguage(String firstLanguage) {
        this.firstLanguage = firstLanguage;
    }

    public Boolean isInterpreterRequired() {
        return interpreterRequired;
    }

    public ServiceUser interpreterRequired(Boolean interpreterRequired) {
        this.interpreterRequired = interpreterRequired;
        return this;
    }

    public void setInterpreterRequired(Boolean interpreterRequired) {
        this.interpreterRequired = interpreterRequired;
    }

    public ZonedDateTime getActivatedDate() {
        return activatedDate;
    }

    public ServiceUser activatedDate(ZonedDateTime activatedDate) {
        this.activatedDate = activatedDate;
        return this;
    }

    public void setActivatedDate(ZonedDateTime activatedDate) {
        this.activatedDate = activatedDate;
    }

    public byte[] getProfilePhoto() {
        return profilePhoto;
    }

    public ServiceUser profilePhoto(byte[] profilePhoto) {
        this.profilePhoto = profilePhoto;
        return this;
    }

    public void setProfilePhoto(byte[] profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getProfilePhotoContentType() {
        return profilePhotoContentType;
    }

    public ServiceUser profilePhotoContentType(String profilePhotoContentType) {
        this.profilePhotoContentType = profilePhotoContentType;
        return this;
    }

    public void setProfilePhotoContentType(String profilePhotoContentType) {
        this.profilePhotoContentType = profilePhotoContentType;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public ServiceUser profilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
        return this;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public String getLastRecordedHeight() {
        return lastRecordedHeight;
    }

    public ServiceUser lastRecordedHeight(String lastRecordedHeight) {
        this.lastRecordedHeight = lastRecordedHeight;
        return this;
    }

    public void setLastRecordedHeight(String lastRecordedHeight) {
        this.lastRecordedHeight = lastRecordedHeight;
    }

    public String getLastRecordedWeight() {
        return lastRecordedWeight;
    }

    public ServiceUser lastRecordedWeight(String lastRecordedWeight) {
        this.lastRecordedWeight = lastRecordedWeight;
        return this;
    }

    public void setLastRecordedWeight(String lastRecordedWeight) {
        this.lastRecordedWeight = lastRecordedWeight;
    }

    public Boolean isHasMedicalCondition() {
        return hasMedicalCondition;
    }

    public ServiceUser hasMedicalCondition(Boolean hasMedicalCondition) {
        this.hasMedicalCondition = hasMedicalCondition;
        return this;
    }

    public void setHasMedicalCondition(Boolean hasMedicalCondition) {
        this.hasMedicalCondition = hasMedicalCondition;
    }

    public String getMedicalConditionSummary() {
        return medicalConditionSummary;
    }

    public ServiceUser medicalConditionSummary(String medicalConditionSummary) {
        this.medicalConditionSummary = medicalConditionSummary;
        return this;
    }

    public void setMedicalConditionSummary(String medicalConditionSummary) {
        this.medicalConditionSummary = medicalConditionSummary;
    }

    public ZonedDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public ServiceUser lastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
        return this;
    }

    public void setLastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public ServiceUser tenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public User getUser() {
        return user;
    }

    public ServiceUser user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Branch getBranch() {
        return branch;
    }

    public ServiceUser branch(Branch branch) {
        this.branch = branch;
        return this;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Employee getRegisteredBy() {
        return registeredBy;
    }

    public ServiceUser registeredBy(Employee employee) {
        this.registeredBy = employee;
        return this;
    }

    public void setRegisteredBy(Employee employee) {
        this.registeredBy = employee;
    }

    public Employee getActivatedBy() {
        return activatedBy;
    }

    public ServiceUser activatedBy(Employee employee) {
        this.activatedBy = employee;
        return this;
    }

    public void setActivatedBy(Employee employee) {
        this.activatedBy = employee;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceUser)) {
            return false;
        }
        return id != null && id.equals(((ServiceUser) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceUser{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", preferredName='" + getPreferredName() + "'" +
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
            ", profilePhotoContentType='" + getProfilePhotoContentType() + "'" +
            ", profilePhotoUrl='" + getProfilePhotoUrl() + "'" +
            ", lastRecordedHeight='" + getLastRecordedHeight() + "'" +
            ", lastRecordedWeight='" + getLastRecordedWeight() + "'" +
            ", hasMedicalCondition='" + isHasMedicalCondition() + "'" +
            ", medicalConditionSummary='" + getMedicalConditionSummary() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", tenantId=" + getTenantId() +
            "}";
    }
}
