import { Moment } from 'moment';
import { ServiceUserGender } from 'app/shared/model/enumerations/service-user-gender.model';
import { MaritalStatus } from 'app/shared/model/enumerations/marital-status.model';
import { Religion } from 'app/shared/model/enumerations/religion.model';

export interface IEquality {
  id?: number;
  gender?: ServiceUserGender;
  maritalStatus?: MaritalStatus;
  religion?: Religion;
  createdDate?: string;
  lastUpdatedDate?: string;
  clientId?: number;
  hasExtraData?: boolean;
  nationalityCountryName?: string;
  nationalityId?: number;
  serviceUserServiceUserCode?: string;
  serviceUserId?: number;
}

export const defaultValue: Readonly<IEquality> = {
  hasExtraData: false,
};
