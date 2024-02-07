import { ICategorieClient, NewCategorieClient } from './categorie-client.model';

export const sampleWithRequiredData: ICategorieClient = {
  id: 'f5f3b2f4-aa97-4821-9df4-e94fd58b3fab',
};

export const sampleWithPartialData: ICategorieClient = {
  id: '517f2c28-2698-451f-bcde-1cd99cd4a2ce',
};

export const sampleWithFullData: ICategorieClient = {
  id: '9e137317-812f-4c29-bbb8-ad2b5fb1a116',
  nume: 'nervously valance',
};

export const sampleWithNewData: NewCategorieClient = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
