import dayjs from 'dayjs/esm';
import { IJTMaterialProcesVerbal } from 'app/entities/jt-material-proces-verbal/jt-material-proces-verbal.model';
import { ICompanie } from 'app/entities/companie/companie.model';
import { IClient } from 'app/entities/client/client.model';
import { IUtilizator } from 'app/entities/utilizator/utilizator.model';

export interface IProcesVerbal {
  id: string;
  data?: dayjs.Dayjs | null;
  ora?: dayjs.Dayjs | null;
  numarProcesVerbal?: number | null;
  reprezentant?: string | null;
  spatii?: string | null;
  suprafata?: number | null;
  rapelDezinsectie?: number | null;
  rapelDeratizare?: number | null;
  garantieDezinsectie?: boolean | null;
  garantieDeratizare?: boolean | null;
  jTMaterialProcesVerbals?: IJTMaterialProcesVerbal[] | null;
  jtmaterialProcesVerbals?: IJTMaterialProcesVerbal[] | null;
  companie?: ICompanie | null;
  client?: IClient | null;
  operator?: IUtilizator | null;
}

export interface ProcesVerbalList {
  id: string;
  ora?: dayjs.Dayjs | null;
  numarProcesVerbal?: number | null;
  reprezentant?: string | null;
  procedura?: string | null;
  produs?: string | null;
  cantitate?: string | null;
  client?: string | null;
  operator?: string | null;
}

export type NewProcesVerbal = Omit<IProcesVerbal, 'id'> & { id: null };
