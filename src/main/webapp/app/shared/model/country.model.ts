import { Moment } from 'moment';

export interface ICountry {
  id?: number;
  countryName?: string;
  countryIsoCode?: string;
  countryFlagUrl?: string;
  countryCallingCode?: string;
  countryTelDigitLength?: number;
  createdDate?: string;
  lastUpdatedDate?: string;
  hasExtraData?: boolean;
}

export const defaultValue: Readonly<ICountry> = {
  hasExtraData: false,
};
