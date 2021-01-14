export interface IEmployeeDesignation {
  id?: number;
  designation?: string;
  description?: string;
  designationDate?: string;
  clientId?: number;
  hasExtraData?: boolean;
}

export const defaultValue: Readonly<IEmployeeDesignation> = {
  hasExtraData: false,
};
