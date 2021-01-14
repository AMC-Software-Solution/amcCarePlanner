import { Moment } from 'moment';

export interface IEmployeeLocation {
  id?: number;
  latitude?: number;
  longitude?: number;
  createdDate?: string;
  lastUpdatedDate?: string;
  clientId?: number;
  hasExtraData?: boolean;
  employeeEmployeeCode?: string;
  employeeId?: number;
}

export const defaultValue: Readonly<IEmployeeLocation> = {
  hasExtraData: false,
};
