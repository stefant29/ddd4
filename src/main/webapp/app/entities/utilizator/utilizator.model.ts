import { ICompanie } from 'app/entities/companie/companie.model';
import { Functie } from 'app/entities/enumerations/functie.model';
import { DDDEntitate } from '../ddd-entitate';

export class IUtilizator extends DDDEntitate {
  id: string;
  nume?: string | null;
  prenume?: string | null;
  functie?: keyof typeof Functie | null;
  email?: string | null;
  companie?: ICompanie | null;
}

export type NewUtilizator = Omit<IUtilizator, 'id'> & { id: null };
