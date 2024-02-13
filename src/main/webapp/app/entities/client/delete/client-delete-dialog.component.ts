import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IClient } from '../client.model';
import { ClientService } from '../service/client.service';

@Component({
  standalone: true,
  templateUrl: './client-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ClientDeleteDialogComponent {
  client?: IClient;

  constructor(
    protected clientService: ClientService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.clientService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
