import { Moment } from 'moment';

export interface IServiceOrder {
  id?: number;
  title?: string;
  serviceDescription?: string;
  serviceRate?: number;
  tenantId?: number;
  lastUpdatedDate?: string;
}

export const defaultValue: Readonly<IServiceOrder> = {};
