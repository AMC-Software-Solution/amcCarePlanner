import { Moment } from 'moment';
import { ServiceUserTitle } from 'app/shared/model/enumerations/service-user-title.model';
import { SupportType } from 'app/shared/model/enumerations/support-type.model';
import { ServiceUserCategory } from 'app/shared/model/enumerations/service-user-category.model';
import { Vulnerability } from 'app/shared/model/enumerations/vulnerability.model';
import { ServicePriority } from 'app/shared/model/enumerations/service-priority.model';
import { Source } from 'app/shared/model/enumerations/source.model';
import { ServiceUserStatus } from 'app/shared/model/enumerations/service-user-status.model';

export interface IServiceUser {
  id?: number;
  title?: ServiceUserTitle;
  firstName?: string;
  middleName?: string;
  lastName?: string;
  preferredName?: string;
  email?: string;
  serviceUserCode?: string;
  dateOfBirth?: string;
  lastVisitDate?: string;
  startDate?: string;
  supportType?: SupportType;
  serviceUserCategory?: ServiceUserCategory;
  vulnerability?: Vulnerability;
  servicePriority?: ServicePriority;
  source?: Source;
  status?: ServiceUserStatus;
  firstLanguage?: string;
  interpreterRequired?: boolean;
  activatedDate?: string;
  profilePhotoContentType?: string;
  profilePhoto?: any;
  profilePhotoUrl?: string;
  lastRecordedHeight?: string;
  lastRecordedWeight?: string;
  hasMedicalCondition?: boolean;
  medicalConditionSummary?: string;
  createdDate?: string;
  lastUpdatedDate?: string;
  clientId?: number;
  hasExtraData?: boolean;
  userEmail?: string;
  userId?: number;
  branchName?: string;
  branchId?: number;
  registeredByEmployeeCode?: string;
  registeredById?: number;
  activatedByEmployeeCode?: string;
  activatedById?: number;
}

export const defaultValue: Readonly<IServiceUser> = {
  interpreterRequired: false,
  hasMedicalCondition: false,
  hasExtraData: false,
};
