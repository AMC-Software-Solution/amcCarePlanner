import { Moment } from 'moment';

export interface ITenant {
  id?: number;
  tenantName?: string;
  tenantDescription?: string;
  tenantLogoContentType?: string;
  tenantLogo?: any;
  tenantLogoUrl?: string;
  tenantContactName?: string;
  tenantPhone?: string;
  tenantEmail?: string;
  createdDate?: string;
  lastUpdatedDate?: string;
}

export const defaultValue: Readonly<ITenant> = {};
