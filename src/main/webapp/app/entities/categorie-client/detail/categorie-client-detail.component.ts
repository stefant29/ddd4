import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ICategorieClient } from '../categorie-client.model';
import { ButtonModule } from 'primeng/button';

@Component({
  standalone: true,
  selector: 'jhi-categorie-client-detail',
  templateUrl: './categorie-client-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe, ButtonModule],
})
export class CategorieClientDetailComponent {
  @Input() categorieClient: ICategorieClient | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  previousState(): void {
    window.history.back();
  }
}
