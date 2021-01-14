import { Moment } from 'moment';

export interface IAccess {
  id?: number;
  keySafeNumber?: string;
  accessDetails?: string;
  createdDate?: string;
  lastUpdatedDate?: string;
  clientId?: number;
  hasExtraData?: boolean;
  serviceUserServiceUserCode?: string;
  serviceUserId?: number;
}

export const defaultValue: Readonly<IAccess> = {
  hasExtraData: false,
};
