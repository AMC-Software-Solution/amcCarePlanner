import { Moment } from 'moment';

export interface IAnswer {
  id?: number;
  answer?: string;
  description?: string;
  attribute1?: string;
  attribute2?: string;
  attribute3?: string;
  attribute4?: string;
  attribute5?: string;
  createdDate?: string;
  lastUpdatedDate?: string;
  clientId?: number;
  questionQuestion?: string;
  questionId?: number;
  serviceUserServiceUserCode?: string;
  serviceUserId?: number;
  recordedByEmployeeCode?: string;
  recordedById?: number;
}

export const defaultValue: Readonly<IAnswer> = {};
