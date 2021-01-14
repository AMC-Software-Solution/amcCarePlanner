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
  createdDate?: string;
  lastUpdatedDate?: string;
  clientId?: number;
  hasExtraData?: boolean;
}

export const defaultValue: Readonly<INotification> = {
  hasExtraData: false,
};
