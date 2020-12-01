import { Moment } from 'moment';

export interface IEmergencyContact {
  id?: number;
  name?: string;
  contactRelationship?: string;
  isKeyHolder?: boolean;
  infoSharingConsentGiven?: boolean;
  preferredContactNumber?: string;
  fullAddress?: string;
  lastUpdatedDate?: string;
  clientId?: number;
  serviceUserServiceUserCode?: string;
  serviceUserId?: number;
}

export const defaultValue: Readonly<IEmergencyContact> = {
  isKeyHolder: false,
  infoSharingConsentGiven: false,
};
