export interface ICurrency {
  id?: number;
  currency?: string;
  currencyIsoCode?: string;
  currencySymbol?: string;
  currencyLogoContentType?: string;
  currencyLogo?: any;
  currencyLogoUrl?: string;
  hasExtraData?: boolean;
}

export const defaultValue: Readonly<ICurrency> = {
  hasExtraData: false,
};
