import { Moment } from 'moment';
import { EmployeeTitle } from 'app/shared/model/enumerations/employee-title.model';
import { EmployeeGender } from 'app/shared/model/enumerations/employee-gender.model';
import { EmployeeContractType } from 'app/shared/model/enumerations/employee-contract-type.model';
import { EmployeeTravelMode } from 'app/shared/model/enumerations/employee-travel-mode.model';
import { EmployeeStatus } from 'app/shared/model/enumerations/employee-status.model';

export interface IEmployee {
  id?: number;
  title?: EmployeeTitle;
  firstName?: string;
  middleInitial?: string;
  lastName?: string;
  preferredName?: string;
  gender?: EmployeeGender;
  employeeCode?: string;
  phone?: string;
  email?: string;
  nationalInsuranceNumber?: string;
  employeeContractType?: EmployeeContractType;
  pinCode?: number;
  transportMode?: EmployeeTravelMode;
  address?: string;
  county?: string;
  postCode?: string;
  dateOfBirth?: string;
  photoContentType?: string;
  photo?: any;
  photoUrl?: string;
  status?: EmployeeStatus;
  employeeBio?: string;
  acruedHolidayHours?: number;
  createdDate?: string;
  lastUpdatedDate?: string;
  clientId?: number;
  hasExtraData?: boolean;
  userEmail?: string;
  userId?: number;
  designationDesignation?: string;
  designationId?: number;
  nationalityCountryName?: string;
  nationalityId?: number;
  branchName?: string;
  branchId?: number;
}

export const defaultValue: Readonly<IEmployee> = {
  hasExtraData: false,
};
