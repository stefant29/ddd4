import { IUser } from 'app/entities/user/user.model';
import { IProcesVerbal } from 'app/entities/proces-verbal/proces-verbal.model';
import { ICompanie } from 'app/entities/companie/companie.model';
import { Functie } from 'app/entities/enumerations/functie.model';

export interface IUtilizator {
  id: string;
  nume?: string | null;
  prenume?: string | null;
  functie?: keyof typeof Functie | null;
  user?: Pick<IUser, 'id'> | null;
  proceseVerbales?: IProcesVerbal[] | null;
  companie?: ICompanie | null;
}

export type NewUtilizator = Omit<IUtilizator, 'id'> & { id: null };
