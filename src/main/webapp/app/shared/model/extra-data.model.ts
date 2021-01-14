import { Moment } from 'moment';
import { DataType } from 'app/shared/model/enumerations/data-type.model';

export interface IExtraData {
  id?: number;
  entityName?: string;
  entityId?: number;
  extraDataKey?: string;
  extraDataValue?: string;
  extraDataValueDataType?: DataType;
  extraDataDescription?: string;
  extraDataDate?: string;
  hasExtraData?: boolean;
}

export const defaultValue: Readonly<IExtraData> = {
  hasExtraData: false,
};
