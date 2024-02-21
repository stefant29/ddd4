import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IUtilizator } from '../utilizator.model';

import { ButtonModule } from 'primeng/button';
import { PrimeNGConfig } from 'primeng/api';

@Component({
  standalone: true,
  selector: 'jhi-utilizator-detail',
  templateUrl: './utilizator-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe, ButtonModule],
})
export class UtilizatorDetailComponent implements OnInit {
  @Input() utilizator: IUtilizator | null = null;

  constructor(
    protected activatedRoute: ActivatedRoute,
    private primengConfig: PrimeNGConfig,
  ) {}

  ngOnInit(): void {
    this.primengConfig.ripple = true;
  }

  previousState(): void {
    window.history.back();
  }
}
