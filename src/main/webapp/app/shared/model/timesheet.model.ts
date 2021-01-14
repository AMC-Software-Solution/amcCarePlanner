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
  createdDate?: string;
  lastUpdatedDate?: string;
  clientId?: number;
  hasExtraData?: boolean;
  taskTaskName?: string;
  taskId?: number;
  serviceUserServiceUserCode?: string;
  serviceUserId?: number;
  careProviderEmployeeCode?: string;
  careProviderId?: number;
}

export const defaultValue: Readonly<ITimesheet> = {
  hasExtraData: false,
};
