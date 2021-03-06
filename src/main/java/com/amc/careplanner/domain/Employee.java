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

import com.amc.careplanner.domain.enumeration.Gender;

import com.amc.careplanner.domain.enumeration.TravelMode;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Employee implements Serializable {

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

    @Size(max = 1)
    @Column(name = "middle_initial", length = 1)
    private String middleInitial;

    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "last_name", length = 25, nullable = false)
    private String lastName;

    @Size(max = 25)
    @Column(name = "preferred_name", length = 25)
    private String preferredName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @NotNull
    @Column(name = "employee_code", nullable = false, unique = true)
    private String employeeCode;

    @Column(name = "social_security_number")
    private String socialSecurityNumber;

    @Column(name = "pin_code")
    private Integer pinCode;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "transport_mode", nullable = false)
    private TravelMode transportMode;

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "county")
    private String county;

    @Column(name = "post_code")
    private String postCode;

    @NotNull
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "last_updated_date")
    private ZonedDateTime lastUpdatedDate;

    @NotNull
    @Column(name = "tenant_id", nullable = false)
    private Long tenantId;

    @ManyToOne
    @JsonIgnoreProperties(value = "employees", allowSetters = true)
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = "employees", allowSetters = true)
    private Country nationality;

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

    public Employee title(Title title) {
        this.title = title;
        return this;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public Employee firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleInitial() {
        return middleInitial;
    }

    public Employee middleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
        return this;
    }

    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }

    public String getLastName() {
        return lastName;
    }

    public Employee lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPreferredName() {
        return preferredName;
    }

    public Employee preferredName(String preferredName) {
        this.preferredName = preferredName;
        return this;
    }

    public void setPreferredName(String preferredName) {
        this.preferredName = preferredName;
    }

    public Gender getGender() {
        return gender;
    }

    public Employee gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public Employee employeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
        return this;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public Employee socialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
        return this;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public Integer getPinCode() {
        return pinCode;
    }

    public Employee pinCode(Integer pinCode) {
        this.pinCode = pinCode;
        return this;
    }

    public void setPinCode(Integer pinCode) {
        this.pinCode = pinCode;
    }

    public TravelMode getTransportMode() {
        return transportMode;
    }

    public Employee transportMode(TravelMode transportMode) {
        this.transportMode = transportMode;
        return this;
    }

    public void setTransportMode(TravelMode transportMode) {
        this.transportMode = transportMode;
    }

    public String getAddress() {
        return address;
    }

    public Employee address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCounty() {
        return county;
    }

    public Employee county(String county) {
        this.county = county;
        return this;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getPostCode() {
        return postCode;
    }

    public Employee postCode(String postCode) {
        this.postCode = postCode;
        return this;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Employee dateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public Employee photo(byte[] photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public Employee photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public Employee photoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
        return this;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public ZonedDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public Employee lastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
        return this;
    }

    public void setLastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public Employee tenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public User getUser() {
        return user;
    }

    public Employee user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Country getNationality() {
        return nationality;
    }

    public Employee nationality(Country country) {
        this.nationality = country;
        return this;
    }

    public void setNationality(Country country) {
        this.nationality = country;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        return id != null && id.equals(((Employee) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", middleInitial='" + getMiddleInitial() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", preferredName='" + getPreferredName() + "'" +
            ", gender='" + getGender() + "'" +
            ", employeeCode='" + getEmployeeCode() + "'" +
            ", socialSecurityNumber='" + getSocialSecurityNumber() + "'" +
            ", pinCode=" + getPinCode() +
            ", transportMode='" + getTransportMode() + "'" +
            ", address='" + getAddress() + "'" +
            ", county='" + getCounty() + "'" +
            ", postCode='" + getPostCode() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            ", photoUrl='" + getPhotoUrl() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", tenantId=" + getTenantId() +
            "}";
    }
}
