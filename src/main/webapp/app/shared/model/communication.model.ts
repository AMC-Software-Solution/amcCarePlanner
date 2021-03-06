import { Moment } from 'moment';
import { CommunicationType } from 'app/shared/model/enumerations/communication-type.model';

export interface ICommunication {
  id?: number;
  communicationType?: CommunicationType;
  note?: string;
  communicationDate?: string;
  attachmentContentType?: string;
  attachment?: any;
  attachmentUrl?: string;
  lastUpdatedDate?: string;
  tenantId?: number;
  serviceUserServiceUserCode?: string;
  serviceUserId?: number;
  communicatedByEmployeeCode?: string;
  communicatedById?: number;
}

export const defaultValue: Readonly<ICommunication> = {};
