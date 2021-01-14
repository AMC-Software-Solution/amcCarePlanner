export interface IRelationshipType {
  id?: number;
  relationType?: string;
  description?: string;
  clientId?: number;
  hasExtraData?: boolean;
}

export const defaultValue: Readonly<IRelationshipType> = {
  hasExtraData: false,
};
