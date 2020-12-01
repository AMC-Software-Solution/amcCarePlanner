import { Moment } from 'moment';
import { DocumentStatus } from 'app/shared/model/enumerations/document-status.model';

export interface IServceUserDocument {
  id?: number;
  documentName?: string;
  documentNumber?: string;
  documentStatus?: DocumentStatus;
  note?: string;
  issuedDate?: string;
  expiryDate?: string;
  uploadedDate?: string;
  documentFileContentType?: string;
  documentFile?: any;
  documentFileUrl?: string;
  lastUpdatedDate?: string;
  clientId?: number;
  ownerServiceUserCode?: string;
  ownerId?: number;
  approvedByEmployeeCode?: string;
  approvedById?: number;
}

export const defaultValue: Readonly<IServceUserDocument> = {};
