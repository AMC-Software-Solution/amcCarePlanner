package com.amc.careplanner.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.amc.careplanner.domain.enumeration.PayrollStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.amc.careplanner.domain.Payroll} entity. This class is used
 * in {@link com.amc.careplanner.web.rest.PayrollResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /payrolls?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PayrollCriteria implements Serializable, Criteria {
    /**
     * Class for filtering PayrollStatus
     */
    public static class PayrollStatusFilter extends Filter<PayrollStatus> {

        public PayrollStatusFilter() {
        }

        public PayrollStatusFilter(PayrollStatusFilter filter) {
            super(filter);
        }

        @Override
        public PayrollStatusFilter copy() {
            return new PayrollStatusFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter paymentDate;

    private StringFilter payPeriod;

    private IntegerFilter totalHoursWorked;

    private BigDecimalFilter grossPay;

    private BigDecimalFilter netPay;

    private BigDecimalFilter totalTax;

    private PayrollStatusFilter payrollStatus;

    private ZonedDateTimeFilter createdDate;

    private ZonedDateTimeFilter lastUpdatedDate;

    private LongFilter clientId;

    private BooleanFilter hasExtraData;

    private LongFilter employeeId;

    private LongFilter timesheetId;

    public PayrollCriteria() {
    }

    public PayrollCriteria(PayrollCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.paymentDate = other.paymentDate == null ? null : other.paymentDate.copy();
        this.payPeriod = other.payPeriod == null ? null : other.payPeriod.copy();
        this.totalHoursWorked = other.totalHoursWorked == null ? null : other.totalHoursWorked.copy();
        this.grossPay = other.grossPay == null ? null : other.grossPay.copy();
        this.netPay = other.netPay == null ? null : other.netPay.copy();
        this.totalTax = other.totalTax == null ? null : other.totalTax.copy();
        this.payrollStatus = other.payrollStatus == null ? null : other.payrollStatus.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.lastUpdatedDate = other.lastUpdatedDate == null ? null : other.lastUpdatedDate.copy();
        this.clientId = other.clientId == null ? null : other.clientId.copy();
        this.hasExtraData = other.hasExtraData == null ? null : other.hasExtraData.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.timesheetId = other.timesheetId == null ? null : other.timesheetId.copy();
    }

    @Override
    public PayrollCriteria copy() {
        return new PayrollCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ZonedDateTimeFilter getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(ZonedDateTimeFilter paymentDate) {
        this.paymentDate = paymentDate;
    }

    public StringFilter getPayPeriod() {
        return payPeriod;
    }

    public void setPayPeriod(StringFilter payPeriod) {
        this.payPeriod = payPeriod;
    }

    public IntegerFilter getTotalHoursWorked() {
        return totalHoursWorked;
    }

    public void setTotalHoursWorked(IntegerFilter totalHoursWorked) {
        this.totalHoursWorked = totalHoursWorked;
    }

    public BigDecimalFilter getGrossPay() {
        return grossPay;
    }

    public void setGrossPay(BigDecimalFilter grossPay) {
        this.grossPay = grossPay;
    }

    public BigDecimalFilter getNetPay() {
        return netPay;
    }

    public void setNetPay(BigDecimalFilter netPay) {
        this.netPay = netPay;
    }

    public BigDecimalFilter getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(BigDecimalFilter totalTax) {
        this.totalTax = totalTax;
    }

    public PayrollStatusFilter getPayrollStatus() {
        return payrollStatus;
    }

    public void setPayrollStatus(PayrollStatusFilter payrollStatus) {
        this.payrollStatus = payrollStatus;
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

    public LongFilter getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(LongFilter employeeId) {
        this.employeeId = employeeId;
    }

    public LongFilter getTimesheetId() {
        return timesheetId;
    }

    public void setTimesheetId(LongFilter timesheetId) {
        this.timesheetId = timesheetId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PayrollCriteria that = (PayrollCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(paymentDate, that.paymentDate) &&
            Objects.equals(payPeriod, that.payPeriod) &&
            Objects.equals(totalHoursWorked, that.totalHoursWorked) &&
            Objects.equals(grossPay, that.grossPay) &&
            Objects.equals(netPay, that.netPay) &&
            Objects.equals(totalTax, that.totalTax) &&
            Objects.equals(payrollStatus, that.payrollStatus) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastUpdatedDate, that.lastUpdatedDate) &&
            Objects.equals(clientId, that.clientId) &&
            Objects.equals(hasExtraData, that.hasExtraData) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(timesheetId, that.timesheetId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        paymentDate,
        payPeriod,
        totalHoursWorked,
        grossPay,
        netPay,
        totalTax,
        payrollStatus,
        createdDate,
        lastUpdatedDate,
        clientId,
        hasExtraData,
        employeeId,
        timesheetId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PayrollCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (paymentDate != null ? "paymentDate=" + paymentDate + ", " : "") +
                (payPeriod != null ? "payPeriod=" + payPeriod + ", " : "") +
                (totalHoursWorked != null ? "totalHoursWorked=" + totalHoursWorked + ", " : "") +
                (grossPay != null ? "grossPay=" + grossPay + ", " : "") +
                (netPay != null ? "netPay=" + netPay + ", " : "") +
                (totalTax != null ? "totalTax=" + totalTax + ", " : "") +
                (payrollStatus != null ? "payrollStatus=" + payrollStatus + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastUpdatedDate != null ? "lastUpdatedDate=" + lastUpdatedDate + ", " : "") +
                (clientId != null ? "clientId=" + clientId + ", " : "") +
                (hasExtraData != null ? "hasExtraData=" + hasExtraData + ", " : "") +
                (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
                (timesheetId != null ? "timesheetId=" + timesheetId + ", " : "") +
            "}";
    }

}
