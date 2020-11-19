import { Moment } from 'moment';

export interface IPowerOfAttorney {
  id?: number;
  powerOfAttorneyConsent?: boolean;
  healthAndWelfare?: boolean;
  healthAndWelfareName?: string;
  propertyAndFinAffairs?: boolean;
  propertyAndFinAffairsName?: string;
  lastUpdatedDate?: string;
  tenantId?: number;
  serviceUserServiceUserCode?: string;
  serviceUserId?: number;
  witnessedByEmployeeCode?: string;
  witnessedById?: number;
}

export const defaultValue: Readonly<IPowerOfAttorney> = {
  powerOfAttorneyConsent: false,
  healthAndWelfare: false,
  propertyAndFinAffairs: false,
};
