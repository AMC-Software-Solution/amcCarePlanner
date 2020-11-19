package com.amc.careplanner.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import com.amc.careplanner.domain.enumeration.PayrollStatus;

/**
 * A DTO for the {@link com.amc.careplanner.domain.Payroll} entity.
 */
public class PayrollDTO implements Serializable {
    
    private Long id;

    @NotNull
    private ZonedDateTime paymentDate;

    @NotNull
    private String payPeriod;

    @NotNull
    private Integer totalHoursWorked;

    @NotNull
    private BigDecimal grossPay;

    @NotNull
    private BigDecimal netPay;

    private BigDecimal totalTax;

    @NotNull
    private PayrollStatus payrollStatus;

    private ZonedDateTime lastUpdatedDate;

    @NotNull
    private Long tenantId;


    private Long employeeId;

    private String employeeEmployeeCode;

    private Long timesheetId;

    private String timesheetDescription;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(ZonedDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPayPeriod() {
        return payPeriod;
    }

    public void setPayPeriod(String payPeriod) {
        this.payPeriod = payPeriod;
    }

    public Integer getTotalHoursWorked() {
        return totalHoursWorked;
    }

    public void setTotalHoursWorked(Integer totalHoursWorked) {
        this.totalHoursWorked = totalHoursWorked;
    }

    public BigDecimal getGrossPay() {
        return grossPay;
    }

    public void setGrossPay(BigDecimal grossPay) {
        this.grossPay = grossPay;
    }

    public BigDecimal getNetPay() {
        return netPay;
    }

    public void setNetPay(BigDecimal netPay) {
        this.netPay = netPay;
    }

    public BigDecimal getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(BigDecimal totalTax) {
        this.totalTax = totalTax;
    }

    public PayrollStatus getPayrollStatus() {
        return payrollStatus;
    }

    public void setPayrollStatus(PayrollStatus payrollStatus) {
        this.payrollStatus = payrollStatus;
    }

    public ZonedDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(ZonedDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeEmployeeCode() {
        return employeeEmployeeCode;
    }

    public void setEmployeeEmployeeCode(String employeeEmployeeCode) {
        this.employeeEmployeeCode = employeeEmployeeCode;
    }

    public Long getTimesheetId() {
        return timesheetId;
    }

    public void setTimesheetId(Long timesheetId) {
        this.timesheetId = timesheetId;
    }

    public String getTimesheetDescription() {
        return timesheetDescription;
    }

    public void setTimesheetDescription(String timesheetDescription) {
        this.timesheetDescription = timesheetDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PayrollDTO)) {
            return false;
        }

        return id != null && id.equals(((PayrollDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PayrollDTO{" +
            "id=" + getId() +
            ", paymentDate='" + getPaymentDate() + "'" +
            ", payPeriod='" + getPayPeriod() + "'" +
            ", totalHoursWorked=" + getTotalHoursWorked() +
            ", grossPay=" + getGrossPay() +
            ", netPay=" + getNetPay() +
            ", totalTax=" + getTotalTax() +
            ", payrollStatus='" + getPayrollStatus() + "'" +
            ", lastUpdatedDate='" + getLastUpdatedDate() + "'" +
            ", tenantId=" + getTenantId() +
            ", employeeId=" + getEmployeeId() +
            ", employeeEmployeeCode='" + getEmployeeEmployeeCode() + "'" +
            ", timesheetId=" + getTimesheetId() +
            ", timesheetDescription='" + getTimesheetDescription() + "'" +
            "}";
    }
}
