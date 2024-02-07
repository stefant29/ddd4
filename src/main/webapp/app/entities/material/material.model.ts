import dayjs from 'dayjs/esm';
import { IJTMaterialProcesVerbal } from 'app/entities/jt-material-proces-verbal/jt-material-proces-verbal.model';
import { ICompanie } from 'app/entities/companie/companie.model';
import { Procedura } from 'app/entities/enumerations/procedura.model';

export interface IMaterial {
  id: string;
  procedura?: keyof typeof Procedura | null;
  factura?: string | null;
  denumire?: string | null;
  lot?: string | null;
  dataAchizitionare?: dayjs.Dayjs | null;
  dataExpirare?: dayjs.Dayjs | null;
  dilutie?: string | null;
  timpContact?: number | null;
  metodaAplicare?: string | null;
  gramaj?: number | null;
  cantitate?: number | null;
  jTMaterialProcesVerbals?: IJTMaterialProcesVerbal[] | null;
  companie?: ICompanie | null;
}

export type NewMaterial = Omit<IMaterial, 'id'> & { id: null };
