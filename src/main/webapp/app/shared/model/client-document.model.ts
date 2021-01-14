import { Moment } from 'moment';
import { ClientDocumentType } from 'app/shared/model/enumerations/client-document-type.model';
import { DocumentStatus } from 'app/shared/model/enumerations/document-status.model';

export interface IClientDocument {
  id?: number;
  documentName?: string;
  documentNumber?: string;
  documentType?: ClientDocumentType;
  documentStatus?: DocumentStatus;
  note?: string;
  issuedDate?: string;
  expiryDate?: string;
  uploadedDate?: string;
  documentFileContentType?: string;
  documentFile?: any;
  documentFileUrl?: string;
  createdDate?: string;
  lastUpdatedDate?: string;
  clientId?: number;
  hasExtraData?: boolean;
  uploadedByEmployeeCode?: string;
  uploadedById?: number;
  approvedByEmployeeCode?: string;
  approvedById?: number;
}

export const defaultValue: Readonly<IClientDocument> = {
  hasExtraData: false,
};
