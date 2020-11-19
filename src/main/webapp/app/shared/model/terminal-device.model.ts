import { Moment } from 'moment';

export interface ITerminalDevice {
  id?: number;
  deviceName?: string;
  deviceModel?: string;
  registeredDate?: string;
  imei?: string;
  simNumber?: string;
  userStartedUsingFrom?: string;
  deviceOnLocationFrom?: string;
  operatingSystem?: string;
  note?: string;
  ownerEntityId?: number;
  ownerEntityName?: string;
  lastUpdatedDate?: string;
  tenantId?: number;
  employeeEmployeeCode?: string;
  employeeId?: number;
}

export const defaultValue: Readonly<ITerminalDevice> = {};
