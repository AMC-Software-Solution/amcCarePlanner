import { Moment } from 'moment';

export interface IEligibility {
  id?: number;
  isEligible?: boolean;
  note?: string;
  createdDate?: string;
  lastUpdatedDate?: string;
  clientId?: number;
  hasExtraData?: boolean;
  eligibilityTypeEligibilityType?: string;
  eligibilityTypeId?: number;
  employeeEmployeeCode?: string;
  employeeId?: number;
}

export const defaultValue: Readonly<IEligibility> = {
  isEligible: false,
  hasExtraData: false,
};
