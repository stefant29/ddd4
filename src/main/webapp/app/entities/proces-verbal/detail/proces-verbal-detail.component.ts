import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IProcesVerbal } from '../proces-verbal.model';

@Component({
  standalone: true,
  selector: 'jhi-proces-verbal-detail',
  templateUrl: './proces-verbal-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ProcesVerbalDetailComponent {
  @Input() procesVerbal: IProcesVerbal | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  previousState(): void {
    window.history.back();
  }
}
