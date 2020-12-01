import { Moment } from 'moment';
import { InvoiceStatus } from 'app/shared/model/enumerations/invoice-status.model';

export interface IInvoice {
  id?: number;
  totalAmount?: number;
  description?: string;
  invoiceNumber?: string;
  generatedDate?: string;
  dueDate?: string;
  paymentDate?: string;
  invoiceStatus?: InvoiceStatus;
  tax?: number;
  attribute1?: string;
  attribute2?: string;
  attribute3?: string;
  attribute4?: string;
  attribute5?: string;
  attribute6?: string;
  attribute7?: string;
  lastUpdatedDate?: string;
  clientId?: number;
  serviceOrderTitle?: string;
  serviceOrderId?: number;
  serviceUserServiceUserCode?: string;
  serviceUserId?: number;
}

export const defaultValue: Readonly<IInvoice> = {};
