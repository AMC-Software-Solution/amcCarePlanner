import { Moment } from 'moment';

export interface IEmployeeLocation {
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

export const defaultValue: Readonly<IEmployeeLocation> = {
  hasExtraData: false,
};
