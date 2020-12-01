import { Moment } from 'moment';

export interface IDocumentType {
  id?: number;
  documentTypeTitle?: string;
  documentTypeDescription?: string;
  lastUpdatedDate?: string;
}

export const defaultValue: Readonly<IDocumentType> = {};
