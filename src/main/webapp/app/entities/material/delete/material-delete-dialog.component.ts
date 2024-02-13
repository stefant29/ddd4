import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IMaterial } from '../material.model';
import { MaterialService } from '../service/material.service';

@Component({
  standalone: true,
  templateUrl: './material-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class MaterialDeleteDialogComponent {
  material?: IMaterial;

  constructor(
    protected materialService: MaterialService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.materialService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
