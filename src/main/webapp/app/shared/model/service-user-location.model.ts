import { Moment } from 'moment';

export interface IServiceUserLocation {
  id?: number;
  latitude?: string;
  longitude?: string;
  createdDate?: string;
  lastUpdatedDate?: string;
  clientId?: number;
  hasExtraData?: boolean;
  employeeEmployeeCode?: string;
  employeeId?: number;
}

export const defaultValue: Readonly<IServiceUserLocation> = {
  hasExtraData: false,
};
