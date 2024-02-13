import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IProcesVerbal } from '../proces-verbal.model';
import { ProcesVerbalService } from '../service/proces-verbal.service';

@Component({
  standalone: true,
  templateUrl: './proces-verbal-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ProcesVerbalDeleteDialogComponent {
  procesVerbal?: IProcesVerbal;

  constructor(
    protected procesVerbalService: ProcesVerbalService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.procesVerbalService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
