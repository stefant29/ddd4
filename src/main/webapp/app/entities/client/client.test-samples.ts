import { IClient, NewClient } from './client.model';

export const sampleWithRequiredData: IClient = {
  id: 'd0f49656-5582-46a8-8679-a160ad00721c',
  denumire: 'yum swiftly terrify',
  codFiscal: 'phew band up',
  adresaPunctLucru: 'wool',
  telefon: 'harm',
  persoanaContact: 'given',
};

export const sampleWithPartialData: IClient = {
  id: '5129af9c-780b-4328-92cf-a612141421f7',
  denumire: 'pfft since airbus',
  codFiscal: 'busily lightly silky',
  numarRegistruComert: 'which ostracize pancake',
  adresaPunctLucru: 'supposing',
  telefon: 'regarding',
  persoanaContact: 'yowza until',
  contract: 'blossom round gee',
  frecventaDezinsectie: 4145,
  frecventaDezinfectie: 19954,
};

export const sampleWithFullData: IClient = {
  id: '8fa1f13d-699d-4bce-83c4-0ab45736cbe4',
  denumire: 'annex but lavish',
  codFiscal: 'sharply collision joshingly',
  numarRegistruComert: 'jaggedly optimistically limb',
  adresaPunctLucru: 'aside phew',
  telefon: 'node',
  email: 'Eduard91@gmail.com',
  persoanaContact: 'grill ugh',
  contract: 'frenetically',
  deratizare: false,
  dezinsectie: false,
  dezinfectie: true,
  frecventaDeratizare: 19164,
  frecventaDezinsectie: 27470,
  frecventaDezinfectie: 8229,
};

export const sampleWithNewData: NewClient = {
  denumire: 'by',
  codFiscal: 'why optimistically corps',
  adresaPunctLucru: 'through shrill benefit',
  telefon: 'ultimately',
  persoanaContact: 'yuck furthermore than',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
