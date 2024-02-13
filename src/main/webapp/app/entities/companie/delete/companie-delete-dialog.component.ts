import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICompanie } from '../companie.model';
import { CompanieService } from '../service/companie.service';

@Component({
  standalone: true,
  templateUrl: './companie-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CompanieDeleteDialogComponent {
  companie?: ICompanie;

  constructor(
    protected companieService: CompanieService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.companieService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
