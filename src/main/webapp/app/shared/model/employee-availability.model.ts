import { Moment } from 'moment';
import { Shift } from 'app/shared/model/enumerations/shift.model';

export interface IEmployeeAvailability {
  id?: number;
  availableForWork?: boolean;
  availableMonday?: boolean;
  availableTuesday?: boolean;
  availableWednesday?: boolean;
  availableThursday?: boolean;
  availableFriday?: boolean;
  availableSaturday?: boolean;
  availableSunday?: boolean;
  preferredShift?: Shift;
  hasExtraData?: boolean;
  lastUpdatedDate?: string;
  clientId?: number;
  employeeEmployeeCode?: string;
  employeeId?: number;
}

export const defaultValue: Readonly<IEmployeeAvailability> = {
  availableForWork: false,
  availableMonday: false,
  availableTuesday: false,
  availableWednesday: false,
  availableThursday: false,
  availableFriday: false,
  availableSaturday: false,
  availableSunday: false,
  hasExtraData: false,
};
