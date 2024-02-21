import { IProcesVerbal } from 'app/entities/proces-verbal/proces-verbal.model';
import { ICompanie } from 'app/entities/companie/companie.model';
import { ICategorieClient } from 'app/entities/categorie-client/categorie-client.model';

export interface IClient {
  id: string;
  denumire?: string | null;
  codFiscal?: string | null;
  numarRegistruComert?: string | null;
  adresaPunctLucru?: string | null;
  telefon?: string | null;
  email?: string | null;
  persoanaContact?: string | null;
  contract?: string | null;
  deratizare?: boolean | null;
  dezinsectie?: boolean | null;
  dezinfectie?: boolean | null;
  frecventaDeratizare?: number | null;
  frecventaDezinsectie?: number | null;
  frecventaDezinfectie?: number | null;
  proceseVerbales?: IProcesVerbal[] | null;
  companie?: ICompanie | null;
  categorie?: ICategorieClient | null;
}

export type NewClient = Omit<IClient, 'id'> & { id: null };
