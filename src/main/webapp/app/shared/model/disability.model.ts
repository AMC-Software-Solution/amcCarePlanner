import { Moment } from 'moment';

export interface IDisability {
  id?: number;
  isDisabled?: boolean;
  note?: string;
  createdDate?: string;
  lastUpdatedDate?: string;
  clientId?: number;
  hasExtraData?: boolean;
  disabilityTypeDisability?: string;
  disabilityTypeId?: number;
  employeeEmployeeCode?: string;
  employeeId?: number;
}

export const defaultValue: Readonly<IDisability> = {
  isDisabled: false,
  hasExtraData: false,
};
