import { Moment } from 'moment';

export interface IClient {
  id?: number;
  clientName?: string;
  clientDescription?: string;
  clientLogoContentType?: string;
  clientLogo?: any;
  clientLogoUrl?: string;
  clientContactName?: string;
  clientPhone?: string;
  clientEmail?: string;
  createdDate?: string;
  lastUpdatedDate?: string;
}

export const defaultValue: Readonly<IClient> = {};
