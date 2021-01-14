export interface IEligibilityType {
  id?: number;
  eligibilityType?: string;
  description?: string;
  hasExtraData?: boolean;
}

export const defaultValue: Readonly<IEligibilityType> = {
  hasExtraData: false,
};
