import { Shift } from 'app/shared/model/enumerations/shift.model';

export interface IEmployeeAvailability {
  id?: number;
  isAvailableForWork?: boolean;
  minimumHoursPerWeek?: number;
  maximumHoursPerWeek?: number;
  leastPreferredShift?: Shift;
  employeeEmployeeCode?: string;
  employeeId?: number;
}

export const defaultValue: Readonly<IEmployeeAvailability> = {
  isAvailableForWork: false,
};
