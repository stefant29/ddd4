import { ICompanie, NewCompanie } from './companie.model';

export const sampleWithRequiredData: ICompanie = {
  id: 2899,
};

export const sampleWithPartialData: ICompanie = {
  id: 31479,
  iD: 'provided dob boohoo',
};

export const sampleWithFullData: ICompanie = {
  id: 21606,
  iD: 'shady mechanically',
  nUME: 'tremendously',
};

export const sampleWithNewData: NewCompanie = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
