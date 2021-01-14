import { Moment } from 'moment';

export interface IServiceOrder {
  id?: number;
  title?: string;
  serviceDescription?: string;
  serviceHourlyRate?: number;
  clientId?: number;
  createdDate?: string;
  lastUpdatedDate?: string;
  hasExtraData?: boolean;
  currencyCurrency?: string;
  currencyId?: number;
}

export const defaultValue: Readonly<IServiceOrder> = {
  hasExtraData: false,
};
