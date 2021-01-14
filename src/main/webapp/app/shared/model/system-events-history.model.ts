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
  callerEmail?: string;
  callerId?: number;
  clientId?: number;
  triggedByEmail?: string;
  triggedById?: number;
}

export const defaultValue: Readonly<ISystemEventsHistory> = {
  isSuspecious: false,
};
