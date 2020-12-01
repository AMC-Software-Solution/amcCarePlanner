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
  lastUpdatedDate?: string;
  clientId?: number;
  serviceUserServiceUserCode?: string;
  serviceUserId?: number;
}

export const defaultValue: Readonly<IMedicalContact> = {};
