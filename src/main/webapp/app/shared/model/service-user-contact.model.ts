import { Moment } from 'moment';

export interface IServiceUserContact {
  id?: number;
  address?: string;
  cityOrTown?: string;
  county?: string;
  postCode?: string;
  telephone?: string;
  email?: string;
  lastUpdatedDate?: string;
  tenantId?: number;
  serviceUserServiceUserCode?: string;
  serviceUserId?: number;
}

export const defaultValue: Readonly<IServiceUserContact> = {};
