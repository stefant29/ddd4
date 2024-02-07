import dayjs from 'dayjs/esm';

import { IProcesVerbal, NewProcesVerbal } from './proces-verbal.model';

export const sampleWithRequiredData: IProcesVerbal = {
  id: 'ba6dbb64-bb70-49f1-94ee-7d8e83730f7e',
  data: dayjs('2024-02-04'),
  numarProcesVerbal: 1507,
};

export const sampleWithPartialData: IProcesVerbal = {
  id: 'cc528ede-7039-4103-8377-4150aac8ee34',
  data: dayjs('2024-02-05'),
  ora: dayjs('2024-02-04T19:16'),
  numarProcesVerbal: 3785,
  reprezentant: 'unto but',
  spatii: 'furthermore worm profile',
  suprafata: 31189,
  rapelDezinsectie: 21360,
  garantieDeratizare: true,
};

export const sampleWithFullData: IProcesVerbal = {
  id: '45ee8e03-4bc7-4e40-a5f8-2f8f678fcc66',
  data: dayjs('2024-02-05'),
  ora: dayjs('2024-02-04T21:00'),
  numarProcesVerbal: 2142,
  reprezentant: 'incidentally down',
  spatii: 'corporation gargantuan notwithstanding',
  suprafata: 9449,
  rapelDezinsectie: 19951,
  rapelDeratizare: 26594,
  garantieDezinsectie: false,
  garantieDeratizare: false,
};

export const sampleWithNewData: NewProcesVerbal = {
  data: dayjs('2024-02-04'),
  numarProcesVerbal: 23672,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
