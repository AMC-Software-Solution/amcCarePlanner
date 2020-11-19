import { Moment } from 'moment';

export interface IAccess {
  id?: number;
  keySafeNumber?: string;
  accessDetails?: string;
  lastUpdatedDate?: string;
  tenantId?: number;
  serviceUserServiceUserCode?: string;
  serviceUserId?: number;
}

export const defaultValue: Readonly<IAccess> = {};
