import { Moment } from 'moment';
import { EmployeeDocumentStatus } from 'app/shared/model/enumerations/employee-document-status.model';

export interface IEmployeeDocument {
  id?: number;
  documentName?: string;
  documentNumber?: string;
  documentStatus?: EmployeeDocumentStatus;
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
  documentTypeDocumentTypeTitle?: string;
  documentTypeId?: number;
  employeeEmployeeCode?: string;
  employeeId?: number;
  approvedByEmployeeCode?: string;
  approvedById?: number;
}

export const defaultValue: Readonly<IEmployeeDocument> = {
  hasExtraData: false,
};
