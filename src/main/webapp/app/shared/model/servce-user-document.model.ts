import { Moment } from 'moment';
import { ServiceUserDocumentStatus } from 'app/shared/model/enumerations/service-user-document-status.model';

export interface IServceUserDocument {
  id?: number;
  documentName?: string;
  documentNumber?: string;
  documentStatus?: ServiceUserDocumentStatus;
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
  ownerServiceUserCode?: string;
  ownerId?: number;
  approvedByEmployeeCode?: string;
  approvedById?: number;
}

export const defaultValue: Readonly<IServceUserDocument> = {
  hasExtraData: false,
};
