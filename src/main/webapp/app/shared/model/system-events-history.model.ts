import { Moment } from 'moment';

export interface ISystemEventsHistory {
  id?: number;
  eventName?: string;
  eventDate?: string;
  eventApi?: string;
  ipAddress?: string;
  eventNote?: string;
  eventEntityName?: string;
  eventEntityId?: number;
  isSuspecious?: boolean;
  clientId?: number;
  triggedByLogin?: string;
  triggedById?: number;
}

export const defaultValue: Readonly<ISystemEventsHistory> = {
  isSuspecious: false,
};
