import { Moment } from 'moment';

export interface IEmergencyContact {
  id?: number;
  name?: string;
  contactRelationship?: string;
  isKeyHolder?: boolean;
  infoSharingConsentGiven?: boolean;
  preferredContactNumber?: string;
  fullAddress?: string;
  createdDate?: string;
  lastUpdatedDate?: string;
  clientId?: number;
  hasExtraData?: boolean;
  serviceUserServiceUserCode?: string;
  serviceUserId?: number;
}

export const defaultValue: Readonly<IEmergencyContact> = {
  isKeyHolder: false,
  infoSharingConsentGiven: false,
  hasExtraData: false,
};
