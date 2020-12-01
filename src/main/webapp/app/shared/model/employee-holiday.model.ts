import { Moment } from 'moment';
import { EmployeeHolidayType } from 'app/shared/model/enumerations/employee-holiday-type.model';

export interface IEmployeeHoliday {
  id?: number;
  description?: string;
  startDate?: string;
  endDate?: string;
  employeeHolidayType?: EmployeeHolidayType;
  approvedDate?: string;
  requestedDate?: string;
  approved?: boolean;
  note?: string;
  lastUpdatedDate?: string;
  clientId?: number;
  employeeEmployeeCode?: string;
  employeeId?: number;
  approvedByEmployeeCode?: string;
  approvedById?: number;
}

export const defaultValue: Readonly<IEmployeeHoliday> = {
  approved: false,
};
