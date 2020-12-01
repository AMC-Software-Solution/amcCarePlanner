import { Moment } from 'moment';
import { RelationType } from 'app/shared/model/enumerations/relation-type.model';

export interface ICarerClientRelation {
  id?: number;
  relationType?: RelationType;
  reason?: string;
  count?: number;
  lastUpdatedDate?: string;
  clientId?: number;
  employeeEmployeeCode?: string;
  employeeId?: number;
  serviceUserServiceUserCode?: string;
  serviceUserId?: number;
}

export const defaultValue: Readonly<ICarerClientRelation> = {};
