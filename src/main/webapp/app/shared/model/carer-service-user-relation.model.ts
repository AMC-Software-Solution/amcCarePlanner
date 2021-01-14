import { Moment } from 'moment';

export interface ICarerServiceUserRelation {
  id?: number;
  reason?: string;
  count?: number;
  createdDate?: string;
  lastUpdatedDate?: string;
  clientId?: number;
  hasExtraData?: boolean;
  relationTypeRelationType?: string;
  relationTypeId?: number;
  employeeEmployeeCode?: string;
  employeeId?: number;
  serviceUserServiceUserCode?: string;
  serviceUserId?: number;
}

export const defaultValue: Readonly<ICarerServiceUserRelation> = {
  hasExtraData: false,
};
