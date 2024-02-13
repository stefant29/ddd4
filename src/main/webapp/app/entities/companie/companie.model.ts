import { IUtilizator } from 'app/entities/utilizator/utilizator.model';
import { IClient } from 'app/entities/client/client.model';
import { IMaterial } from 'app/entities/material/material.model';
import { IProcesVerbal } from 'app/entities/proces-verbal/proces-verbal.model';

export interface ICompanie {
  id: string;
  nume?: string | null;
  utilizatoris?: IUtilizator[] | null;
  clientis?: IClient[] | null;
  materiales?: IMaterial[] | null;
  proceseVerbales?: IProcesVerbal[] | null;
}

export type NewCompanie = Omit<ICompanie, 'id'> & { id: null };
