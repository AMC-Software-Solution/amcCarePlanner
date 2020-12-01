import { Moment } from 'moment';
import { PayrollStatus } from 'app/shared/model/enumerations/payroll-status.model';

export interface IPayroll {
  id?: number;
  paymentDate?: string;
  payPeriod?: string;
  totalHoursWorked?: number;
  grossPay?: number;
  netPay?: number;
  totalTax?: number;
  payrollStatus?: PayrollStatus;
  lastUpdatedDate?: string;
  clientId?: number;
  employeeEmployeeCode?: string;
  employeeId?: number;
  timesheetDescription?: string;
  timesheetId?: number;
}

export const defaultValue: Readonly<IPayroll> = {};
