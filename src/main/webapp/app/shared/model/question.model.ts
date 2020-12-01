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
  lastUpdatedDate?: string;
  clientId?: number;
}

export const defaultValue: Readonly<IQuestion> = {
  optional: false,
};
