import dayjs from 'dayjs/esm';

import { IMaterial, NewMaterial } from './material.model';

export const sampleWithRequiredData: IMaterial = {
  id: 'f57563e6-57aa-4804-ac28-3b5b06d73a83',
  factura: 'plate ick',
  denumire: 'safari qua anenst',
  lot: 'joint',
};

export const sampleWithPartialData: IMaterial = {
  id: '5bd11c0e-c257-475c-bec1-61e216662741',
  procedura: 'DERATIZARE',
  factura: 'once hmph',
  denumire: 'consequently across apropos',
  lot: 'peel bustling',
  dilutie: 'tick footstep',
  timpContact: 13022,
  metodaAplicare: 'faithfully near',
  gramaj: 5840,
};

export const sampleWithFullData: IMaterial = {
  id: '5afd01ec-c6ac-4128-94ee-d872a7fd4759',
  procedura: 'DEZINFECTIE',
  factura: 'unwieldy',
  denumire: 'restrain',
  lot: 'objectify',
  dataAchizitionare: dayjs('2024-02-04'),
  dataExpirare: dayjs('2024-02-04'),
  dilutie: 'midnight thermometer yet',
  timpContact: 2327,
  metodaAplicare: 'lecture glass',
  gramaj: 25530,
  cantitate: 29125,
};

export const sampleWithNewData: NewMaterial = {
  factura: 'huzzah bah hurl',
  denumire: 'times',
  lot: 'times gee',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
