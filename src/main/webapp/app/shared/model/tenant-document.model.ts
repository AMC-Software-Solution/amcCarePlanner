import { Moment } from 'moment';
import { TenantDocumentType } from 'app/shared/model/enumerations/tenant-document-type.model';
import { DocumentStatus } from 'app/shared/model/enumerations/document-status.model';

export interface ITenantDocument {
  id?: number;
  documentName?: string;
  documentCode?: string;
  documentNumber?: string;
  documentType?: TenantDocumentType;
  documentStatus?: DocumentStatus;
  note?: string;
  issuedDate?: string;
  expiryDate?: string;
  uploadedDate?: string;
  documentFileContentType?: string;
  documentFile?: any;
  documentFileUrl?: string;
  lastUpdatedDate?: string;
  tenantId?: number;
  uploadedByEmployeeCode?: string;
  uploadedById?: number;
  approvedByEmployeeCode?: string;
  approvedById?: number;
}

export const defaultValue: Readonly<ITenantDocument> = {};
