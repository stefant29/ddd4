import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IUtilizator } from '../utilizator.model';
import { UtilizatorService } from '../service/utilizator.service';

@Component({
  standalone: true,
  templateUrl: './utilizator-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class UtilizatorDeleteDialogComponent {
  utilizator?: IUtilizator;

  constructor(
    protected utilizatorService: UtilizatorService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.utilizatorService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
