import { Moment } from 'moment';
import { ServiceUserEventStatus } from 'app/shared/model/enumerations/service-user-event-status.model';
import { ServiceUserEventType } from 'app/shared/model/enumerations/service-user-event-type.model';
import { Priority } from 'app/shared/model/enumerations/priority.model';

export interface IServiceUserEvent {
  id?: number;
  eventTitle?: string;
  description?: string;
  serviceUserEventStatus?: ServiceUserEventStatus;
  serviceUserEventType?: ServiceUserEventType;
  priority?: Priority;
  note?: string;
  dateOfEvent?: string;
  createdDate?: string;
  lastUpdatedDate?: string;
  clientId?: number;
  hasExtraData?: boolean;
  reportedByEmployeeCode?: string;
  reportedById?: number;
  assignedToEmployeeCode?: string;
  assignedToId?: number;
  serviceUserServiceUserCode?: string;
  serviceUserId?: number;
}

export const defaultValue: Readonly<IServiceUserEvent> = {
  hasExtraData: false,
};
