import { Moment } from 'moment';

export interface IBranch {
  id?: number;
  name?: string;
  address?: string;
  telephone?: string;
  contactName?: string;
  branchEmail?: string;
  photoContentType?: string;
  photo?: any;
  photoUrl?: string;
  createdDate?: string;
  lastUpdatedDate?: string;
  hasExtraData?: boolean;
  clientClientName?: string;
  clientId?: number;
}

export const defaultValue: Readonly<IBranch> = {
  hasExtraData: false,
};
