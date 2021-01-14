import { Moment } from 'moment';

export interface IDocumentType {
  id?: number;
  documentTypeTitle?: string;
  documentTypeDescription?: string;
  createdDate?: string;
  lastUpdatedDate?: string;
  hasExtraData?: boolean;
}

export const defaultValue: Readonly<IDocumentType> = {
  hasExtraData: false,
};
