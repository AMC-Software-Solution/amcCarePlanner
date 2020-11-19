import { Moment } from 'moment';

export interface IEligibility {
  id?: number;
  note?: string;
  lastUpdatedDate?: string;
  tenantId?: number;
  eligibilityTypeEligibilityType?: string;
  eligibilityTypeId?: number;
  employeeEmployeeCode?: string;
  employeeId?: number;
}

export const defaultValue: Readonly<IEligibility> = {};
