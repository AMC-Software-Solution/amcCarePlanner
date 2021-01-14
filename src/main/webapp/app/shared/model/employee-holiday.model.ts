import { Moment } from 'moment';
import { EmployeeHolidayType } from 'app/shared/model/enumerations/employee-holiday-type.model';
import { HolidayStatus } from 'app/shared/model/enumerations/holiday-status.model';

export interface IEmployeeHoliday {
  id?: number;
  description?: string;
  startDate?: string;
  endDate?: string;
  employeeHolidayType?: EmployeeHolidayType;
  approvedDate?: string;
  requestedDate?: string;
  holidayStatus?: HolidayStatus;
  note?: string;
  rejectionReason?: string;
  createdDate?: string;
  lastUpdatedDate?: string;
  clientId?: number;
  hasExtraData?: boolean;
  employeeEmployeeCode?: string;
  employeeId?: number;
  approvedByEmployeeCode?: string;
  approvedById?: number;
}

export const defaultValue: Readonly<IEmployeeHoliday> = {
  hasExtraData: false,
};
