package com.amc.careplanner.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A MedicalContact.
 */
@Entity
@Table(name = "medical_contact")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MedicalContact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "doctor_name")
    private String doctorName;

    @Column(name = "doctor_surgery")
    private String doctorSurgery;

    @Column(name = "doctor_address")
    private String doctorAddress;

    @Column(name = "doctor_phone")
    private String doctorPhone;

    @Column(name = "last_visited_doctor")
    private ZonedDateTime lastVisitedDoctor;

    @Column(name = "district_nurse_name")
    private String districtNurseName;

    @Column(name = "district_nurse_phone")
    private String districtNursePhone;

    @Column(name = "care_manager_name")
    private String careManagerName;

    @Column(name = "care_manager_phone")
    private String careManagerPhone;

    @Column(name = "last_updated_date")
    private ZonedDateTime lastUpdatedDate;

    @NotNull
    @Column(name = "tenant_id", nullable = false)
    private Long tenantId;

    @OneToOne
    @JoinColumn(unique = true)
    private ServiceUser serviceUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public MedicalContact doctorName(String doctorName) {
        this.doctorName = doctorName;
        return this;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorSurgery() {
        return doctorSurgery;
    }

    public MedicalContact doctorSurgery(String doctorSurgery) {
        this.doctorSurgery = doctorSurgery;
        return this;
    }

    public void setDoctorSurgery(String doctorSurgery) {
        this.doctorSurgery = doctorSurgery;
    }

    public String getDoctorAddress() {
        return doctorAddress;
    }

    public MedicalContact doctorAddress(String doctorAddress) {
        this.doctorAddress = doctorAddress;
        return this;
    }

    public void setDoctorAddress(String doctorAddress) {
        this.doctorAddress = doctorAddress;
    }

    public String getDoctorPhone() {
        return doctorPhone;
    }

    public MedicalContact doctorPhone(String doctorPhone) {
        this.doctorPhone = doctorPhone;
        return this;
    }

    public void setDoctorPhone(String doctorPhone) {
        this.doctorPhone = doctorPhone;
    }

    public ZonedDateTime getLastVisitedDoctor() {
        return lastVisitedDoctor;
    }

    public MedicalContact lastVisitedDoctor(ZonedDateTime lastVisitedDoctor) {
        this.lastVisitedDoctor = lastVisitedDoctor;
        return this;
    }

    public void setLastVisitedDoctor(ZonedDateTime lastVisitedDoctor) {
        this.lastVisitedDoctor = lastVisitedDoctor;
    }

    public String getDistrictNurseName() {
        return districtNurseName;
    }

    public MedicalContact districtNurseName(String districtNurseName) {
        this.districtNurseName = districtNurseName;
        return this;
    }

    public void setDistrictNurseName(String districtNurseName) {
        this.districtNurseName = districtNurseName;
    }

    public String getDistrictNursePhone() {
        return districtNursePhone;
    }

    public MedicalContact districtNursePhone(String districtNursePhone) {
        this.districtNursePhone = districtNursePhone;
        return this;
    }

    public void setDistrictNursePhone(String districtNursePhone) {
        this.districtNursePhone = districtNursePhone;
    }

    public String getCareManagerName() {
        return careManagerName;
    }

    public MedicalContact careManagerName(String careManagerName) {
        this.careManagerName = careManagerName;
        return this;
    }

    public void setCareManagerName(String careManagerName) {
        this.careManagerName = careManagerName;
    }

    public String getCareManagerPhone() {
        return careManagerPhone;
    }

    public MedicalContact careManagerPhone(String careManagerPhone) {
        this.careManagerPhone = careManagerPhone;
        return this;
    }

    public void setCareManagerPhone(String careManagerPhone) {
        this.careManagerPhone = careManagerPhone;
    }

    public ZonedDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public MedicalContact lastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
        return this;
    }

    public void setLastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public MedicalContact tenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public ServiceUser getServiceUser() {
        return serviceUser;
    }

    public MedicalContact serviceUser(ServiceUser serviceUser) {
        this.serviceUser = serviceUser;
        return this;
    }

    public void setServiceUser(ServiceUser serviceUser) {
        this.serviceUser = serviceUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MedicalContact)) {
            return false;
        }
        return id != null && id.equals(((MedicalContact) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MedicalContact{" +
            "id=" + getId() +
            ", doctorName='" + getDoctorName() + "'" +
            ", doctorSurgery='" + getDoctorSurgery() + "'" +
            ", doctorAddress='" + getDoctorAddress() + "'" +
            ", doctorPhone='" + getDoctorPhone() + "'" +
            ", lastVisitedDoctor='" + getLastVisitedDoctor() + "'" +
            ", districtNurseName='" + getDistrictNurseName() + "'" +
            ", districtNursePhone='" + getDistrictNursePhone() + "'" +
            ", careManagerName='" + getCareManagerName() + "'" +
            ", careManagerPhone='" + getCareManagerPhone() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", tenantId=" + getTenantId() +
            "}";
    }
}
