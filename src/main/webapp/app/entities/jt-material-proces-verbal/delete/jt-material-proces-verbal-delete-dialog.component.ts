import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IJTMaterialProcesVerbal } from '../jt-material-proces-verbal.model';
import { JTMaterialProcesVerbalService } from '../service/jt-material-proces-verbal.service';

@Component({
  standalone: true,
  templateUrl: './jt-material-proces-verbal-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class JTMaterialProcesVerbalDeleteDialogComponent {
  jTMaterialProcesVerbal?: IJTMaterialProcesVerbal;

  constructor(
    protected jTMaterialProcesVerbalService: JTMaterialProcesVerbalService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.jTMaterialProcesVerbalService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
