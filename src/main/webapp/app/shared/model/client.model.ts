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
  enabled?: boolean;
  reason?: string;
  lastUpdatedDate?: string;
  hasExtraData?: boolean;
}

export const defaultValue: Readonly<IClient> = {
  enabled: false,
  hasExtraData: false,
};
