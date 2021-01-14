import { Moment } from 'moment';
import { TravelMode } from 'app/shared/model/enumerations/travel-mode.model';
import { TravelStatus } from 'app/shared/model/enumerations/travel-status.model';

export interface ITravel {
  id?: number;
  travelMode?: TravelMode;
  distanceToDestination?: number;
  timeToDestination?: number;
  actualDistanceRequired?: number;
  actualTimeRequired?: number;
  travelStatus?: TravelStatus;
  createdDate?: string;
  lastUpdatedDate?: string;
  clientId?: number;
  hasExtraData?: boolean;
  taskTaskName?: string;
  taskId?: number;
}

export const defaultValue: Readonly<ITravel> = {
  hasExtraData: false,
};
