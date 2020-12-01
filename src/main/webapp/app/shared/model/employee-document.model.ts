import { Moment } from 'moment';
import { DocumentStatus } from 'app/shared/model/enumerations/document-status.model';

export interface IEmployeeDocument {
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
  documentTypeDocumentTypeTitle?: string;
  documentTypeId?: number;
  employeeEmployeeCode?: string;
  employeeId?: number;
  approvedByEmployeeCode?: string;
  approvedById?: number;
}

export const defaultValue: Readonly<IEmployeeDocument> = {};
