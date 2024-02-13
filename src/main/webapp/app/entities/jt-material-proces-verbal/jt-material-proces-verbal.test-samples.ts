import { IJTMaterialProcesVerbal, NewJTMaterialProcesVerbal } from './jt-material-proces-verbal.model';

export const sampleWithRequiredData: IJTMaterialProcesVerbal = {
  id: '40034e32-2e7b-4d8d-8fa8-5b2036c34919',
};

export const sampleWithPartialData: IJTMaterialProcesVerbal = {
  id: '7b1bb890-3beb-42c9-a091-c438ca2790cb',
  cantitate: 12530,
};

export const sampleWithFullData: IJTMaterialProcesVerbal = {
  id: '31893cb2-66d3-45f1-9f42-16e7d6d7f41f',
  procedura: 'DEZINFECTIE',
  cantitate: 14500,
};

export const sampleWithNewData: NewJTMaterialProcesVerbal = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
