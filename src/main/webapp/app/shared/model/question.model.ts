import { Moment } from 'moment';

export interface IQuestion {
  id?: number;
  question?: string;
  description?: string;
  attribute1?: string;
  attribute2?: string;
  attribute3?: string;
  attribute4?: string;
  attribute5?: string;
  optional?: boolean;
  createdDate?: string;
  lastUpdatedDate?: string;
  clientId?: number;
  hasExtraData?: boolean;
}

export const defaultValue: Readonly<IQuestion> = {
  optional: false,
  hasExtraData: false,
};
