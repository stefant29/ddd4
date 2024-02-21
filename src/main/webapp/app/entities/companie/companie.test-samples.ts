import { ICompanie, NewCompanie } from './companie.model';

export const sampleWithRequiredData: ICompanie = {
  id: '310efd24-9591-481f-94c6-940c78912a5c',
};

export const sampleWithPartialData: ICompanie = {
  id: '7c9ba1f1-3074-4fc0-bd15-d068f195974d',
};

export const sampleWithFullData: ICompanie = {
  id: '7a318b6a-76d3-47b8-a392-e446337713f2',
  nume: 'feisty',
};

export const sampleWithNewData: NewCompanie = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
