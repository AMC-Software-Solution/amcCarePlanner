import { Moment } from 'moment';

export interface IEmployeeLocation {
  id?: number;
  latitude?: number;
  longitude?: number;
  lastUpdatedDate?: string;
  clientId?: number;
  employeeEmployeeCode?: string;
  employeeId?: number;
}

export const defaultValue: Readonly<IEmployeeLocation> = {};
