import { Moment } from 'moment';

export interface INotification {
  id?: number;
  title?: string;
  body?: string;
  notificationDate?: string;
  imageContentType?: string;
  image?: any;
  imageUrl?: string;
  senderId?: number;
  receiverId?: number;
  lastUpdatedDate?: string;
  clientId?: number;
}

export const defaultValue: Readonly<INotification> = {};
