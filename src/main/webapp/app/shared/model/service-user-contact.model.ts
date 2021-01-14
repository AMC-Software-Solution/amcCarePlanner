import { Moment } from 'moment';

export interface IServiceUserContact {
  id?: number;
  address?: string;
  cityOrTown?: string;
  county?: string;
  postCode?: string;
  telephone?: string;
  createdDate?: string;
  lastUpdatedDate?: string;
  clientId?: number;
  hasExtraData?: boolean;
  serviceUserServiceUserCode?: string;
  serviceUserId?: number;
}

export const defaultValue: Readonly<IServiceUserContact> = {
  hasExtraData: false,
};
