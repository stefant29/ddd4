import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IProcesVerbal } from '../proces-verbal.model';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { IJTMaterialProcesVerbal } from 'app/entities/jt-material-proces-verbal/jt-material-proces-verbal.model';
import { TooltipModule } from 'primeng/tooltip';

@Component({
  standalone: true,
  selector: 'jhi-proces-verbal-detail',
  templateUrl: './proces-verbal-detail.component.html',
  imports: [
    SharedModule,
    RouterModule,
    DurationPipe,
    FormatMediumDatetimePipe,
    FormatMediumDatePipe,
    ButtonModule,
    TableModule,
    TooltipModule,
  ],
})
export class ProcesVerbalDetailComponent implements OnInit {
  @Input() procesVerbal: IProcesVerbal | null = null;

  jtMaterialeDeratizari: Array<IJTMaterialProcesVerbal> = [];
  jtMaterialeDezinsectii: Array<IJTMaterialProcesVerbal> = [];
  jtMaterialeDezinfectii: Array<IJTMaterialProcesVerbal> = [];

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    if (!!this.procesVerbal && !!this.procesVerbal.jTMaterialProcesVerbals) {
      this.jtMaterialeDeratizari = this.procesVerbal?.jTMaterialProcesVerbals.filter(
        (item: IJTMaterialProcesVerbal) => item.procedura === 'DERATIZARE',
      );
      this.jtMaterialeDezinsectii = this.procesVerbal?.jTMaterialProcesVerbals.filter(
        (item: IJTMaterialProcesVerbal) => item.procedura === 'DEZINSECTIE',
      );
      this.jtMaterialeDezinfectii = this.procesVerbal?.jTMaterialProcesVerbals.filter(
        (item: IJTMaterialProcesVerbal) => item.procedura === 'DEZINFECTIE',
      );
    } else {
      console.log('ELSE');
    }
  }

  previousState(): void {
    window.history.back();
  }
}
