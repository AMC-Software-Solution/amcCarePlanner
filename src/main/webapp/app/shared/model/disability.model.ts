import { Moment } from 'moment';

export interface IDisability {
  id?: number;
  note?: string;
  lastUpdatedDate?: string;
  clientId?: number;
  disabilityTypeDisability?: string;
  disabilityTypeId?: number;
  employeeEmployeeCode?: string;
  employeeId?: number;
}

export const defaultValue: Readonly<IDisability> = {};
