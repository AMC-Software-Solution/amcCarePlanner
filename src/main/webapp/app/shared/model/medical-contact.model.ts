import { Moment } from 'moment';

export interface IMedicalContact {
  id?: number;
  doctorName?: string;
  doctorSurgery?: string;
  doctorAddress?: string;
  doctorPhone?: string;
  lastVisitedDoctor?: string;
  districtNurseName?: string;
  districtNursePhone?: string;
  careManagerName?: string;
  careManagerPhone?: string;
  createdDate?: string;
  lastUpdatedDate?: string;
  clientId?: number;
  hasExtraData?: boolean;
  serviceUserServiceUserCode?: string;
  serviceUserId?: number;
}

export const defaultValue: Readonly<IMedicalContact> = {
  hasExtraData: false,
};
