import { IClient } from 'app/entities/client/client.model';

export interface ICategorieClient {
  id: string;
  nume?: string | null;
  clients?: IClient[] | null;
}

export type NewCategorieClient = Omit<ICategorieClient, 'id'> & { id: null };
