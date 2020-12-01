import { Moment } from 'moment';
import { Title } from 'app/shared/model/enumerations/title.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';
import { EmployeeContractType } from 'app/shared/model/enumerations/employee-contract-type.model';
import { TravelMode } from 'app/shared/model/enumerations/travel-mode.model';

export interface IEmployee {
  id?: number;
  title?: Title;
  firstName?: string;
  middleInitial?: string;
  lastName?: string;
  preferredName?: string;
  gender?: Gender;
  employeeCode?: string;
  email?: string;
  nationalInsuranceNumber?: string;
  employeeContractType?: EmployeeContractType;
  pinCode?: number;
  transportMode?: TravelMode;
  address?: string;
  county?: string;
  postCode?: string;
  dateOfBirth?: string;
  photoContentType?: string;
  photo?: any;
  photoUrl?: string;
  acruedHolidayHours?: number;
  lastUpdatedDate?: string;
  clientId?: number;
  userLogin?: string;
  userId?: number;
  nationalityCountryName?: string;
  nationalityId?: number;
}

export const defaultValue: Readonly<IEmployee> = {};
