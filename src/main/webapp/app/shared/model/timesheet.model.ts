import { Moment } from 'moment';

export interface ITimesheet {
  id?: number;
  description?: string;
  timesheetDate?: string;
  startTime?: string;
  endTime?: string;
  hoursWorked?: number;
  breakStartTime?: string;
  breakEndTime?: string;
  note?: string;
  lastUpdatedDate?: string;
  tenantId?: number;
  serviceOrderTitle?: string;
  serviceOrderId?: number;
  serviceUserServiceUserCode?: string;
  serviceUserId?: number;
  careProviderEmployeeCode?: string;
  careProviderId?: number;
}

export const defaultValue: Readonly<ITimesheet> = {};
