import { Moment } from 'moment';

export interface ICountry {
  id?: number;
  countryName?: string;
  countryIsoCode?: string;
  countryFlagUrl?: string;
  countryCallingCode?: string;
  countryTelDigitLength?: number;
  lastUpdatedDate?: string;
}

export const defaultValue: Readonly<ICountry> = {};
