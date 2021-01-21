import { Moment } from 'moment';
import { PayrollStatus } from 'app/shared/model/enumerations/payroll-status.model';

export interface IPayroll {
  id?: number;
  paymentDate?: string;
  payPeriod?: string;
  totalHoursWorked?: number;
  grossPay?: string;
  netPay?: string;
  totalTax?: string;
  payrollStatus?: PayrollStatus;
  createdDate?: string;
  lastUpdatedDate?: string;
  clientId?: number;
  hasExtraData?: boolean;
  employeeEmployeeCode?: string;
  employeeId?: number;
  timesheetDescription?: string;
  timesheetId?: number;
}

export const defaultValue: Readonly<IPayroll> = {
  hasExtraData: false,
};
