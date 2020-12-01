import { Moment } from 'moment';
import { ServiceUserEventStatus } from 'app/shared/model/enumerations/service-user-event-status.model';
import { ServiceUserEventType } from 'app/shared/model/enumerations/service-user-event-type.model';
import { ServicePriority } from 'app/shared/model/enumerations/service-priority.model';

export interface IServiceUserEvent {
  id?: number;
  eventTitle?: string;
  description?: string;
  serviceUserEventStatus?: ServiceUserEventStatus;
  serviceUserEventType?: ServiceUserEventType;
  priority?: ServicePriority;
  note?: string;
  dateOfEvent?: string;
  lastUpdatedDate?: string;
  clientId?: number;
  reportedByEmployeeCode?: string;
  reportedById?: number;
  assignedToEmployeeCode?: string;
  assignedToId?: number;
  serviceUserServiceUserCode?: string;
  serviceUserId?: number;
}

export const defaultValue: Readonly<IServiceUserEvent> = {};
