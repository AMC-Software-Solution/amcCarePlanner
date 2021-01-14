import { Moment } from 'moment';
import { TaskStatus } from 'app/shared/model/enumerations/task-status.model';

export interface ITask {
  id?: number;
  taskName?: string;
  description?: string;
  dateOfTask?: string;
  startTime?: string;
  endTime?: string;
  status?: TaskStatus;
  createdDate?: string;
  lastUpdatedDate?: string;
  clientId?: number;
  hasExtraData?: boolean;
  serviceUserServiceUserCode?: string;
  serviceUserId?: number;
  assignedToEmployeeCode?: string;
  assignedToId?: number;
  serviceOrderTitle?: string;
  serviceOrderId?: number;
  createdByEmployeeCode?: string;
  createdById?: number;
  allocatedByEmployeeCode?: string;
  allocatedById?: number;
}

export const defaultValue: Readonly<ITask> = {
  hasExtraData: false,
};
