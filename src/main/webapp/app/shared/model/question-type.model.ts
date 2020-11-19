import { Moment } from 'moment';

export interface IQuestionType {
  id?: number;
  questionType?: string;
  lastUpdatedDate?: string;
}

export const defaultValue: Readonly<IQuestionType> = {};
