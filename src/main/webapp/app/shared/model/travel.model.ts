import { Moment } from 'moment';
import { TravelMode } from 'app/shared/model/enumerations/travel-mode.model';

export interface ITravel {
  id?: number;
  travelMode?: TravelMode;
  distanceToDestination?: number;
  timeToDestination?: number;
  actualDistanceRequired?: number;
  actualTimeRequired?: number;
  lastUpdatedDate?: string;
  tenantId?: number;
  taskTaskName?: string;
  taskId?: number;
}

export const defaultValue: Readonly<ITravel> = {};
