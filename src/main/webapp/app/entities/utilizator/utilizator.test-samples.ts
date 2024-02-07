import { IUtilizator, NewUtilizator } from './utilizator.model';

export const sampleWithRequiredData: IUtilizator = {
  id: 'c216fa72-c893-4261-9c68-3d1457cc0847',
  nume: 'midst',
  prenume: 'absent',
  functie: 'SUPERADMIN',
};

export const sampleWithPartialData: IUtilizator = {
  id: 'f25d094d-ce79-4dee-9a6d-9224460202c7',
  nume: 'mime',
  prenume: 'mallet loop',
  functie: 'OPERATOR',
};

export const sampleWithFullData: IUtilizator = {
  id: '66eac693-548b-4ad1-8010-d19c3fb605f1',
  nume: 'hence while and',
  prenume: 'styling ouch',
  functie: 'ADMIN',
};

export const sampleWithNewData: NewUtilizator = {
  nume: 'magnet frenzy fitting',
  prenume: 'behind',
  functie: 'ADMIN',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
