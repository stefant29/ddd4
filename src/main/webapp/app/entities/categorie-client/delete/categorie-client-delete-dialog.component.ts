import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICategorieClient } from '../categorie-client.model';
import { CategorieClientService } from '../service/categorie-client.service';

@Component({
  standalone: true,
  templateUrl: './categorie-client-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CategorieClientDeleteDialogComponent {
  categorieClient?: ICategorieClient;

  constructor(
    protected categorieClientService: CategorieClientService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.categorieClientService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
