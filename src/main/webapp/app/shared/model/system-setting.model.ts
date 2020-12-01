import { Moment } from 'moment';

export interface ISystemSetting {
  id?: number;
  fieldName?: string;
  fieldValue?: string;
  defaultValue?: string;
  settingEnabled?: boolean;
  createdDate?: string;
  updatedDate?: string;
  clientId?: number;
}

export const defaultValue: Readonly<ISystemSetting> = {
  settingEnabled: false,
};
