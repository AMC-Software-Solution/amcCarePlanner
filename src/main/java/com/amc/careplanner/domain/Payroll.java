package com.amc.careplanner.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

import com.amc.careplanner.domain.enumeration.PayrollStatus;

/**
 * A Payroll.
 */
@Entity
@Table(name = "payroll")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Payroll implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "payment_date", nullable = false)
    private ZonedDateTime paymentDate;

    @NotNull
    @Column(name = "pay_period", nullable = false)
    private String payPeriod;

    @NotNull
    @Column(name = "total_hours_worked", nullable = false)
    private Integer totalHoursWorked;

    @NotNull
    @Column(name = "gross_pay", nullable = false)
    private String grossPay;

    @NotNull
    @Column(name = "net_pay", nullable = false)
    private String netPay;

    @Column(name = "total_tax")
    private String totalTax;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "payroll_status", nullable = false)
    private PayrollStatus payrollStatus;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "last_updated_date")
    private ZonedDateTime lastUpdatedDate;

    @NotNull
    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @Column(name = "has_extra_data")
    private Boolean hasExtraData;

    @ManyToOne
    @JsonIgnoreProperties(value = "payrolls", allowSetters = true)
    private Employee employee;

    @ManyToOne
    @JsonIgnoreProperties(value = "payrolls", allowSetters = true)
    private Timesheet timesheet;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getPaymentDate() {
        return paymentDate;
    }

    public Payroll paymentDate(ZonedDateTime paymentDate) {
        this.paymentDate = paymentDate;
        return this;
    }

    public void setPaymentDate(ZonedDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPayPeriod() {
        return payPeriod;
    }

    public Payroll payPeriod(String payPeriod) {
        this.payPeriod = payPeriod;
        return this;
    }

    public void setPayPeriod(String payPeriod) {
        this.payPeriod = payPeriod;
    }

    public Integer getTotalHoursWorked() {
        return totalHoursWorked;
    }

    public Payroll totalHoursWorked(Integer totalHoursWorked) {
        this.totalHoursWorked = totalHoursWorked;
        return this;
    }

    public void setTotalHoursWorked(Integer totalHoursWorked) {
        this.totalHoursWorked = totalHoursWorked;
    }

    public String getGrossPay() {
        return grossPay;
    }

    public Payroll grossPay(String grossPay) {
        this.grossPay = grossPay;
        return this;
    }

    public void setGrossPay(String grossPay) {
        this.grossPay = grossPay;
    }

    public String getNetPay() {
        return netPay;
    }

    public Payroll netPay(String netPay) {
        this.netPay = netPay;
        return this;
    }

    public void setNetPay(String netPay) {
        this.netPay = netPay;
    }

    public String getTotalTax() {
        return totalTax;
    }

    public Payroll totalTax(String totalTax) {
        this.totalTax = totalTax;
        return this;
    }

    public void setTotalTax(String totalTax) {
        this.totalTax = totalTax;
    }

    public PayrollStatus getPayrollStatus() {
        return payrollStatus;
    }

    public Payroll payrollStatus(PayrollStatus payrollStatus) {
        this.payrollStatus = payrollStatus;
        return this;
    }

    public void setPayrollStatus(PayrollStatus payrollStatus) {
        this.payrollStatus = payrollStatus;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public Payroll createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public Payroll lastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
        return this;
    }

    public void setLastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Long getClientId() {
        return clientId;
    }

    public Payroll clientId(Long clientId) {
        this.clientId = clientId;
        return this;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Boolean isHasExtraData() {
        return hasExtraData;
    }

    public Payroll hasExtraData(Boolean hasExtraData) {
        this.hasExtraData = hasExtraData;
        return this;
    }

    public void setHasExtraData(Boolean hasExtraData) {
        this.hasExtraData = hasExtraData;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Payroll employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Timesheet getTimesheet() {
        return timesheet;
    }

    public Payroll timesheet(Timesheet timesheet) {
        this.timesheet = timesheet;
        return this;
    }

    public void setTimesheet(Timesheet timesheet) {
        this.timesheet = timesheet;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Payroll)) {
            return false;
        }
        return id != null && id.equals(((Payroll) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Payroll{" +
            "id=" + getId() +
            ", paymentDate='" + getPaymentDate() + "'" +
            ", payPeriod='" + getPayPeriod() + "'" +
            ", totalHoursWorked=" + getTotalHoursWorked() +
            ", grossPay='" + getGrossPay() + "'" +
            ", netPay='" + getNetPay() + "'" +
            ", totalTax='" + getTotalTax() + "'" +
            ", payrollStatus='" + getPayrollStatus() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", clientId=" + getClientId() +
            ", hasExtraData='" + isHasExtraData() + "'" +
            "}";
    }
}
