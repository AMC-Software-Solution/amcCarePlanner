import { Moment } from 'moment';

export interface IServiceUserLocation {
  id?: number;
  latitude?: number;
  longitude?: number;
  lastUpdatedDate?: string;
  tenantId?: number;
  employeeEmployeeCode?: string;
  employeeId?: number;
}

export const defaultValue: Readonly<IServiceUserLocation> = {};
