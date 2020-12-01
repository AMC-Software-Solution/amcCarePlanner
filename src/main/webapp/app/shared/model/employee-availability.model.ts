import { Shift } from 'app/shared/model/enumerations/shift.model';

export interface IEmployeeAvailability {
  id?: number;
  isAvailableForWorkWeekDays?: boolean;
  minimumHoursPerWeekWeekDays?: number;
  maximumHoursPerWeekWeekDays?: number;
  isAvailableForWorkWeekEnds?: boolean;
  minimumHoursPerWeekWeekEnds?: number;
  maximumHoursPerWeekWeekEnds?: number;
  leastPreferredShift?: Shift;
  employeeEmployeeCode?: string;
  employeeId?: number;
}

export const defaultValue: Readonly<IEmployeeAvailability> = {
  isAvailableForWorkWeekDays: false,
  isAvailableForWorkWeekEnds: false,
};
