import { Moment } from 'moment';

export interface IConsent {
  id?: number;
  title?: string;
  description?: string;
  giveConsent?: boolean;
  arrangements?: string;
  serviceUserSignature?: string;
  signatureImageContentType?: string;
  signatureImage?: any;
  signatureImageUrl?: string;
  consentDate?: string;
  createdDate?: string;
  lastUpdatedDate?: string;
  clientId?: number;
  hasExtraData?: boolean;
  serviceUserServiceUserCode?: string;
  serviceUserId?: number;
  witnessedByEmployeeCode?: string;
  witnessedById?: number;
}

export const defaultValue: Readonly<IConsent> = {
  giveConsent: false,
  hasExtraData: false,
};
