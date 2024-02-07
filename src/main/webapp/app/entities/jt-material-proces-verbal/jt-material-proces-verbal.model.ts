import { IMaterial } from 'app/entities/material/material.model';
import { IProcesVerbal } from 'app/entities/proces-verbal/proces-verbal.model';
import { Procedura } from 'app/entities/enumerations/procedura.model';

export interface IJTMaterialProcesVerbal {
  id: string;
  procedura?: keyof typeof Procedura | null;
  cantitate?: number | null;
  produs?: IMaterial | null;
  procesVerbal?: IProcesVerbal | null;
}

export type NewJTMaterialProcesVerbal = Omit<IJTMaterialProcesVerbal, 'id'> & { id: null };
