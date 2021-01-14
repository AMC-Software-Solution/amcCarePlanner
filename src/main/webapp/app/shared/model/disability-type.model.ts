export interface IDisabilityType {
  id?: number;
  disability?: string;
  description?: string;
  hasExtraData?: boolean;
}

export const defaultValue: Readonly<IDisabilityType> = {
  hasExtraData: false,
};
