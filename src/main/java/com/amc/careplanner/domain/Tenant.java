package com.amc.careplanner.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Tenant.
 */
@Entity
@Table(name = "tenant")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Tenant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "tenant_name", nullable = false)
    private String tenantName;

    @Column(name = "tenant_description")
    private String tenantDescription;

    @Lob
    @Column(name = "tenant_logo")
    private byte[] tenantLogo;

    @Column(name = "tenant_logo_content_type")
    private String tenantLogoContentType;

    @Column(name = "tenant_logo_url")
    private String tenantLogoUrl;

    @Column(name = "tenant_contact_name")
    private String tenantContactName;

    @Column(name = "tenant_phone")
    private String tenantPhone;

    @Column(name = "tenant_email")
    private String tenantEmail;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "last_updated_date")
    private ZonedDateTime lastUpdatedDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenantName() {
        return tenantName;
    }

    public Tenant tenantName(String tenantName) {
        this.tenantName = tenantName;
        return this;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getTenantDescription() {
        return tenantDescription;
    }

    public Tenant tenantDescription(String tenantDescription) {
        this.tenantDescription = tenantDescription;
        return this;
    }

    public void setTenantDescription(String tenantDescription) {
        this.tenantDescription = tenantDescription;
    }

    public byte[] getTenantLogo() {
        return tenantLogo;
    }

    public Tenant tenantLogo(byte[] tenantLogo) {
        this.tenantLogo = tenantLogo;
        return this;
    }

    public void setTenantLogo(byte[] tenantLogo) {
        this.tenantLogo = tenantLogo;
    }

    public String getTenantLogoContentType() {
        return tenantLogoContentType;
    }

    public Tenant tenantLogoContentType(String tenantLogoContentType) {
        this.tenantLogoContentType = tenantLogoContentType;
        return this;
    }

    public void setTenantLogoContentType(String tenantLogoContentType) {
        this.tenantLogoContentType = tenantLogoContentType;
    }

    public String getTenantLogoUrl() {
        return tenantLogoUrl;
    }

    public Tenant tenantLogoUrl(String tenantLogoUrl) {
        this.tenantLogoUrl = tenantLogoUrl;
        return this;
    }

    public void setTenantLogoUrl(String tenantLogoUrl) {
        this.tenantLogoUrl = tenantLogoUrl;
    }

    public String getTenantContactName() {
        return tenantContactName;
    }

    public Tenant tenantContactName(String tenantContactName) {
        this.tenantContactName = tenantContactName;
        return this;
    }

    public void setTenantContactName(String tenantContactName) {
        this.tenantContactName = tenantContactName;
    }

    public String getTenantPhone() {
        return tenantPhone;
    }

    public Tenant tenantPhone(String tenantPhone) {
        this.tenantPhone = tenantPhone;
        return this;
    }

    public void setTenantPhone(String tenantPhone) {
        this.tenantPhone = tenantPhone;
    }

    public String getTenantEmail() {
        return tenantEmail;
    }

    public Tenant tenantEmail(String tenantEmail) {
        this.tenantEmail = tenantEmail;
        return this;
    }

    public void setTenantEmail(String tenantEmail) {
        this.tenantEmail = tenantEmail;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public Tenant createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public Tenant lastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
        return this;
    }

    public void setLastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tenant)) {
            return false;
        }
        return id != null && id.equals(((Tenant) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tenant{" +
            "id=" + getId() +
            ", tenantName='" + getTenantName() + "'" +
            ", tenantDescription='" + getTenantDescription() + "'" +
            ", tenantLogo='" + getTenantLogo() + "'" +
            ", tenantLogoContentType='" + getTenantLogoContentType() + "'" +
            ", tenantLogoUrl='" + getTenantLogoUrl() + "'" +
            ", tenantContactName='" + getTenantContactName() + "'" +
            ", tenantPhone='" + getTenantPhone() + "'" +
            ", tenantEmail='" + getTenantEmail() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            "}";
    }
}
