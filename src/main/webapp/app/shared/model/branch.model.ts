import { Moment } from 'moment';

export interface IBranch {
  id?: number;
  name?: string;
  address?: string;
  telephone?: string;
  lastUpdatedDate?: string;
  tenantTenantName?: string;
  tenantId?: number;
}

export const defaultValue: Readonly<IBranch> = {};
