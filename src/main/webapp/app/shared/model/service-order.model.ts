import { Moment } from 'moment';

export interface IServiceOrder {
  id?: number;
  title?: string;
  serviceDescription?: string;
  serviceHourlyRate?: number;
  clientId?: number;
  lastUpdatedDate?: string;
  currencyCurrency?: string;
  currencyId?: number;
}

export const defaultValue: Readonly<IServiceOrder> = {};
