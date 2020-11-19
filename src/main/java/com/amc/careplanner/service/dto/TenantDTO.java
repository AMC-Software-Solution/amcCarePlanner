package com.amc.careplanner.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.amc.careplanner.domain.Tenant} entity.
 */
public class TenantDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String tenantName;

    private String tenantDescription;

    @Lob
    private byte[] tenantLogo;

    private String tenantLogoContentType;
    private String tenantLogoUrl;

    private String tenantContactName;

    private String tenantPhone;

    private String tenantEmail;

    private ZonedDateTime createdDate;

    private ZonedDateTime lastUpdatedDate;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getTenantDescription() {
        return tenantDescription;
    }

    public void setTenantDescription(String tenantDescription) {
        this.tenantDescription = tenantDescription;
    }

    public byte[] getTenantLogo() {
        return tenantLogo;
    }

    public void setTenantLogo(byte[] tenantLogo) {
        this.tenantLogo = tenantLogo;
    }

    public String getTenantLogoContentType() {
        return tenantLogoContentType;
    }

    public void setTenantLogoContentType(String tenantLogoContentType) {
        this.tenantLogoContentType = tenantLogoContentType;
    }

    public String getTenantLogoUrl() {
        return tenantLogoUrl;
    }

    public void setTenantLogoUrl(String tenantLogoUrl) {
        this.tenantLogoUrl = tenantLogoUrl;
    }

    public String getTenantContactName() {
        return tenantContactName;
    }

    public void setTenantContactName(String tenantContactName) {
        this.tenantContactName = tenantContactName;
    }

    public String getTenantPhone() {
        return tenantPhone;
    }

    public void setTenantPhone(String tenantPhone) {
        this.tenantPhone = tenantPhone;
    }

    public String getTenantEmail() {
        return tenantEmail;
    }

    public void setTenantEmail(String tenantEmail) {
        this.tenantEmail = tenantEmail;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TenantDTO)) {
            return false;
        }

        return id != null && id.equals(((TenantDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TenantDTO{" +
            "id=" + getId() +
            ", tenantName='" + getTenantName() + "'" +
            ", tenantDescription='" + getTenantDescription() + "'" +
            ", tenantLogo='" + getTenantLogo() + "'" +
            ", tenantLogoUrl='" + getTenantLogoUrl() + "'" +
            ", tenantContactName='" + getTenantContactName() + "'" +
            ", tenantPhone='" + getTenantPhone() + "'" +
            ", tenantEmail='" + getTenantEmail() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            "}";
    }
}
