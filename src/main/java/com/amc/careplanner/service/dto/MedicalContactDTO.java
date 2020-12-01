package com.amc.careplanner.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.amc.careplanner.domain.MedicalContact} entity.
 */
public class MedicalContactDTO implements Serializable {
    
    private Long id;

    private String doctorName;

    private String doctorSurgery;

    private String doctorAddress;

    private String doctorPhone;

    private ZonedDateTime lastVisitedDoctor;

    private String districtNurseName;

    private String districtNursePhone;

    private String careManagerName;

    private String careManagerPhone;

    private ZonedDateTime lastUpdatedDate;

    @NotNull
    private Long clientId;


    private Long serviceUserId;

    private String serviceUserServiceUserCode;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorSurgery() {
        return doctorSurgery;
    }

    public void setDoctorSurgery(String doctorSurgery) {
        this.doctorSurgery = doctorSurgery;
    }

    public String getDoctorAddress() {
        return doctorAddress;
    }

    public void setDoctorAddress(String doctorAddress) {
        this.doctorAddress = doctorAddress;
    }

    public String getDoctorPhone() {
        return doctorPhone;
    }

    public void setDoctorPhone(String doctorPhone) {
        this.doctorPhone = doctorPhone;
    }

    public ZonedDateTime getLastVisitedDoctor() {
        return lastVisitedDoctor;
    }

    public void setLastVisitedDoctor(ZonedDateTime lastVisitedDoctor) {
        this.lastVisitedDoctor = lastVisitedDoctor;
    }

    public String getDistrictNurseName() {
        return districtNurseName;
    }

    public void setDistrictNurseName(String districtNurseName) {
        this.districtNurseName = districtNurseName;
    }

    public String getDistrictNursePhone() {
        return districtNursePhone;
    }

    public void setDistrictNursePhone(String districtNursePhone) {
        this.districtNursePhone = districtNursePhone;
    }

    public String getCareManagerName() {
        return careManagerName;
    }

    public void setCareManagerName(String careManagerName) {
        this.careManagerName = careManagerName;
    }

    public String getCareManagerPhone() {
        return careManagerPhone;
    }

    public void setCareManagerPhone(String careManagerPhone) {
        this.careManagerPhone = careManagerPhone;
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

    public Long getServiceUserId() {
        return serviceUserId;
    }

    public void setServiceUserId(Long serviceUserId) {
        this.serviceUserId = serviceUserId;
    }

    public String getServiceUserServiceUserCode() {
        return serviceUserServiceUserCode;
    }

    public void setServiceUserServiceUserCode(String serviceUserServiceUserCode) {
        this.serviceUserServiceUserCode = serviceUserServiceUserCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MedicalContactDTO)) {
            return false;
        }

        return id != null && id.equals(((MedicalContactDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MedicalContactDTO{" +
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
            ", clientId=" + getClientId() +
            ", serviceUserId=" + getServiceUserId() +
            ", serviceUserServiceUserCode='" + getServiceUserServiceUserCode() + "'" +
            "}";
    }
}
